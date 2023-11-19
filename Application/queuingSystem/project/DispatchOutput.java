package com.example.application.views.main.queuingSystem.project;

import static com.example.application.views.main.queuingSystem.project.Controller.events;
import static com.example.application.views.main.queuingSystem.project.Generator.sources;
import static com.example.application.views.main.queuingSystem.project.Processor.*;

public class DispatchOutput {
    protected static Buffer buffer;

    public DispatchOutput (Buffer buffer) {
        DispatchOutput.buffer = buffer;
    }

    public Request takeRequestFromBuff() {
        return buffer.getOutRequest();
    }

    public void requestGoToDevice(Request request) {
        buffer.deleteRequest(request);
        events.add(new Event(EventType.REQUEST_CHOICE_BUF, request.getTaskId(), "Заявка " + request.getTaskId()
                + " была отправлена на обслуживание " + "999"));
    }

    // Ставим заявку на прибор и возвращаем номер, куда поставили.
    public int setRequestOnDevice(Request request, double currentTime) {
        boolean flag = false;
        int count = 2;
        int tmp = 0;
        while (!flag) {
            // Идем до конца в поисках пустого прибора.
            for (int i = cursor; i < devices.size(); i++) {
                if (devices.get(i).isFree(currentTime)) {
                    if (devices.get(i).getStartTime() != 0 ) {
                        events.add(new Event(EventType.REQUEST_FINISH_DEVICE, findEventByNum(devices.get(i)),
                                "Заявка " + devices.get(i).getCurrentProcessedRequest().getTaskId()
                                        + " была обработана девайсом под номером " + (devices.get(i).getDeviceNumber()+1)));

                    }
                    devices.get(i).putRequest(request, currentTime);
                    cursor = i;
                    cursor++;
                    tmp = cursor;
                    if (cursor == devices.size()) {cursor =  0;}
                    return tmp;
                }
            }
            count--;
            if (count == 0) { flag = true; cursor = 0;}
            if (!flag) { cursor = 0; }
        }
        return -1;
    }

    public void checkStep(double currTime) {
        if (!finishDevices.isEmpty() && !buffer.isEmpty()) {
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
                    cursor++;
                }
                if (!buffer.isEmpty()) {
                    Request req = takeRequestFromBuff();
                    int deviceNumber = setRequestOnDeviceByDevice(req, finishDevices.get(indexDev).getDeviceNumber());
                    if (deviceNumber != -1) {
                        double nextReleaseTime= Controller.getNextReleaseTime();
                        devices.get(deviceNumber - 1).setEndTime(nextReleaseTime);
                        requestGoToDevice(req);
                        events.add(new Event(EventType.REQUEST_CHOICE_DEVICE, req.getTaskId(),
                                "Заявка " + req.getTaskId() + " поступила на обслуживание девайса под номером " + (deviceNumber)));
                        sources.get(req.getSourceNumber() - 1).addBufferTimes(currTime - req.getTimeOfMaking());
                        sources.get(req.getSourceNumber() - 1).addServiceTimes(nextReleaseTime - currTime);
                        sources.get(req.getSourceNumber() - 1).addTimeUse(nextReleaseTime - req.getTimeOfMaking());
                        sources.get(req.getSourceNumber() - 1).addTimeWait(currTime - req.getTimeOfMaking());
                        devices.get(deviceNumber - 1).addTimeOfWork(nextReleaseTime - currTime);
                    }
                }
            }
            finishDevices.clear();
        }
    }

    public int setRequestOnDeviceByDevice(Request request, int dev) {
        boolean flag = false;
        int count = 2;
        int tmp = 0;
        while (!flag) {
            // Идем до конца в поисках пустого прибора.
            for (int i = cursor; i < devices.size(); i++) {
                if (devices.get(i).getDeviceNumber() == dev) {
                    if (devices.get(i).getStartTime() != 0 ) {
                        events.add(new Event(EventType.REQUEST_FINISH_DEVICE, findEventByNum(devices.get(i)),
                                "Заявка " + devices.get(i).getCurrentProcessedRequest().getTaskId()
                                        + " была обработана девайсом под номером " + (devices.get(i).getDeviceNumber()+1)));
                    }
                    double time = devices.get(i).getEndTime();
                    devices.get(i).putRequest(request, time);
                    cursor = dev;
                    cursor++;
                    tmp = cursor;
                    if (cursor == devices.size()) {cursor =  0;}
                    return tmp;
                }
            }
            count--;
            if (count == 0) { flag = true; }
            if (!flag) { cursor = 0; }
        }
        return -1;
    }

    public String findEventByNum(Device dev) {
        int s = events.size();
        for (int i = s - 1; i >= 0; i--) {
            Event curEv = events.get(i);
            int curNum = getNumOfDev(curEv);
            if (curNum - 1 == dev.getDeviceNumber() && curEv.getType() == EventType.REQUEST_CHOICE_DEVICE) {
                return curEv.getRequestId();
            }
        }
        return "";
    }

    public int getNumOfDev(Event event) {
        String string = event.getInformation();
        int i = string.lastIndexOf(" ") + 1;
        string = string.substring(i);
        return Integer.parseInt(string);
    }
}