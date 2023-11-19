package com.example.application.views.main.queuingSystem.project;

public class Device {
    private int deviceNumber;
    private Request currentProcessedRequest = null;
    private double endTime;
    private double busyTime;
    private double startTime;

    public Device(int deviceNumber) {
        this.deviceNumber = deviceNumber;
    }

    public double getStartTime() {
        return startTime;
    }

    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void putRequest(Request request, double currentTime) {
        setStartTime(currentTime);
        request.setTimeOfUsing(currentTime);
        currentProcessedRequest = request;
    }

    public int getDeviceNumber() {
        return deviceNumber;
    }

    public double getEndTime() {
        return endTime;
    }

    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }

    public double getBusyTime() {
        return busyTime;
    }

    public void addTimeOfWork(double time){
        busyTime += time;
    }

    public boolean isFree(double currTime) {
        return currTime >= endTime;
    }

    public Request getCurrentProcessedRequest() {
        return currentProcessedRequest;
    }
}
