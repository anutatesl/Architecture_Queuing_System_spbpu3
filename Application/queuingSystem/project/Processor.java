package com.example.application.views.main.queuingSystem.project;

import java.util.ArrayList;

import static com.example.application.views.main.queuingSystem.project.Controller.events;
import static com.example.application.views.main.queuingSystem.project.DispatchOutput.buffer;
import static com.example.application.views.main.queuingSystem.project.Generator.sources;


public class Processor {
    private static int countOfDevices;
    protected static ArrayList<Device> devices;
    protected static ArrayList<Device> finishDevices = new ArrayList<>();
    private static DispatchOutput dispatchOutput;
    protected static int cursor = 0;

    public Processor(int countOfDevices, DispatchOutput dispatchOutput) {
        Processor.countOfDevices = countOfDevices;
        Processor.dispatchOutput = dispatchOutput;
        devices = new ArrayList<>();
        for (int i = 0; i < countOfDevices; i++) {
            devices.add(new Device(i));
        }
    }

    public static ArrayList<Device> getDevices() {
        return devices;
    }

    public void startProcessing(double currTime) {
        Request request = dispatchOutput.takeRequestFromBuff();
        int deviceNumber = dispatchOutput.setRequestOnDevice(request, currTime);
        if (deviceNumber != -1) {
            double nextReleaseTime= Controller.getNextReleaseTime();
            devices.get(deviceNumber - 1).setEndTime(nextReleaseTime);
            dispatchOutput.requestGoToDevice(request);
            events.add(new Event(EventType.REQUEST_CHOICE_DEVICE, request.getTaskId(),
                    "Заявка " + request.getTaskId() + " поступила на обслуживание девайса под номером " + (deviceNumber)));
            sources.get(request.getSourceNumber() - 1).addBufferTimes(currTime - request.getTimeOfMaking());
            sources.get(request.getSourceNumber() - 1).addServiceTimes(nextReleaseTime - currTime);
            sources.get(request.getSourceNumber() - 1).addTimeUse(nextReleaseTime - request.getTimeOfMaking()); //время пребывания заявки в системе
            sources.get(request.getSourceNumber() - 1).addTimeWait(currTime - request.getTimeOfMaking()); //время ожидания
            devices.get(deviceNumber - 1).addTimeOfWork(nextReleaseTime - currTime);
        }
        finishDevices.clear();
    }


    public void fillFreeDevices(double currTime) {
        for (Device currentDevice : devices) {
            if (currentDevice.isFree(currTime) && currentDevice.getStartTime() != 0.0) {
                if (finishDevices.isEmpty()) {
                    finishDevices.add(currentDevice);
                } else {
                    int currSize = finishDevices.size();
                    boolean isAdd = false;
                    for (int i = 0; i < currSize; i++) {
                        if (finishDevices.get(i).getEndTime() > currentDevice.getEndTime()) {
                            finishDevices.add(i, currentDevice);
                            isAdd = true;
                            break;
                        }
                    }
                    if (!isAdd) {
                        finishDevices.add(currSize, currentDevice);
                    }
                }
            }
        }
    }

    public void checkNextStep(double currTime) {
        dispatchOutput.checkStep(currTime);
    }
    public void finishWork(double currTime) {
        if (!finishDevices.isEmpty()) {
            if (!buffer.isEmpty()) {
                for (int i = 0; i < finishDevices.size(); i++) {
                    int indexDev = -1;
                    boolean flag = false;
                    while (!flag) {
                        for (int j = 0; j < finishDevices.size(); j++) {
                            if (finishDevices.get(j).getDeviceNumber() == cursor) {
                                indexDev = j;
                                flag = true;
                            }
                        }
                        cursor++; if(cursor > devices.size()) {cursor = 0; }
                    }
                    if (!buffer.isEmpty()) {
                        Request insRequest = dispatchOutput.takeRequestFromBuff();
                        int deviceNumber = dispatchOutput.setRequestOnDeviceByDevice(insRequest, finishDevices.get(indexDev).getDeviceNumber());
                        if (deviceNumber != -1) {
                            double nextReleaseTime= Controller.getNextReleaseTime();
                            devices.get(deviceNumber - 1).setEndTime(nextReleaseTime);
                            dispatchOutput.requestGoToDevice(insRequest);
                            events.add(new Event(EventType.REQUEST_CHOICE_DEVICE, insRequest.getTaskId(),
                                    "Заявка " + insRequest.getTaskId() + " поступила на обслуживание девайса под номером " + (deviceNumber)));
                            sources.get(insRequest.getSourceNumber() - 1).addBufferTimes(currTime - insRequest.getTimeOfMaking());
                            sources.get(insRequest.getSourceNumber() - 1).addServiceTimes(nextReleaseTime - currTime);
                            sources.get(insRequest.getSourceNumber() - 1).addTimeUse(nextReleaseTime - insRequest.getTimeOfMaking());
                            sources.get(insRequest.getSourceNumber() - 1).addTimeWait(currTime - insRequest.getTimeOfMaking());
                            devices.get(deviceNumber - 1).addTimeOfWork(nextReleaseTime - currTime);
                        }
                    }
                }
            } else {
                for (Device curDev : finishDevices) {
                    events.add(new Event(EventType.REQUEST_FINISH_DEVICE, dispatchOutput.findEventByNum(curDev),
                            "Заявка " + curDev.getCurrentProcessedRequest().getTaskId()
                                    + " была обработана девайсом под номером " + (curDev.getDeviceNumber()+1)));
                    curDev.setStartTime(0);
                    if (isAllDevicesFinish()) {
                        Controller.setWorkingTime(curDev.getEndTime());
                        break;
                    }
                }
            }
            finishDevices.clear();
        }
    }

    public boolean isAllDevicesFinish() {
        for (Device curDev : devices) {
            if (curDev.getStartTime() != 0) {
                return false;
            }
        }
        return true;
    }

    public void updateDevices() {
        for (int i = 0; i < devices.size(); i++) {
            devices.set(i, new Device(i));
        }
        cursor = 0;
    }
}
