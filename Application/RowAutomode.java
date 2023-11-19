package com.example.application.views.main;

import com.vaadin.flow.component.html.Div;

public class RowAutomode extends Div {
    private Object sourceNumber;
    private Object countOfRequests;
    private Object probabilityOfFailure;
    private Object averageTimeUse;
    private Object averageTimeWait;
    private Object averageServiceTime;
    private Object dispersionServiceTime;
    private Object dispersionTimeWait;
    private Object deviceNumber;
    private Object coefOfUse;

    public RowAutomode(Object sourceNumber, Object countOfRequests, Object probabilityOfFailure, Object averageTimeUse,
                       Object averageTimeWait, Object averageServiceTime,Object dispersionServiceTime, Object dispersionTimeWait) {
        this.sourceNumber = sourceNumber;
        this.countOfRequests = countOfRequests;
        this.probabilityOfFailure = probabilityOfFailure;
        this.averageTimeUse = averageTimeUse;
        this.averageTimeWait = averageTimeWait;
        this.averageServiceTime = averageServiceTime;
        this.dispersionServiceTime = dispersionServiceTime;
        this.dispersionTimeWait = dispersionTimeWait;
    }

    public RowAutomode(Object deviceNumber, Object coefOfUse) {
        this.deviceNumber = deviceNumber;
        this.coefOfUse= coefOfUse;
    }

    public Object getCoefOfUse() {
        return coefOfUse;
    }

    public Object getDeviceNumber() {
        return deviceNumber;
    }

    public Object getDispersionTimeWait() {
        return dispersionTimeWait;
    }

    public Object getAverageTimeWait() {
        return averageTimeWait;
    }

    public Object getDispersionServiceTimeUse() {
        return dispersionServiceTime;
    }

    public Object getAverageTimeUse() {
        return averageTimeUse;
    }

    public Object getCountOfRequests() {
        return countOfRequests;
    }

    public Object getAverageServiceTime() {
        return averageServiceTime;
    }

    public Object getProbabilityOfFailure() {
        return probabilityOfFailure;
    }

    public Object getSourceNumber() {
        return sourceNumber;
    }
}
