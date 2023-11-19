package com.example.application.views.main.queuingSystem.project;

public class Request {
    private double timeOfMaking;
    private int sourceNumber;
    private int requestNumber;
    private double timeOfUsing;
    private String requestId;

    public Request(int requestNumber, int sourceNumber, double timeOfMaking, double timeOfUsing) {
        this.requestId = Integer.toString(sourceNumber) + '.' + requestNumber;
        this.requestNumber = requestNumber;
        this.sourceNumber = sourceNumber;
        this.timeOfMaking = timeOfMaking;
        this.timeOfUsing = timeOfUsing;
    }

    public String getTaskId() {
        return requestId;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public double getTimeOfMaking() {
        return timeOfMaking;
    }

    public void setTimeOfUsing(double timeOfUsing) {
        this.timeOfUsing = timeOfUsing;
    }
}
