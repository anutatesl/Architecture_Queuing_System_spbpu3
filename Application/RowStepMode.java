package com.example.application.views.main;

import com.vaadin.flow.component.html.Div;

public class RowStepMode extends Div  {
    private Object sourceNumber;
    private Object countOfRequests;
    private Object countOfFailure;
    private Object bufferNumber;
    private Object bufferState;
    private Object bufferRequest;
    private Object deviceNumber;
    private Object deviceState;
    private Object deviceRequest;

    public RowStepMode(String descript, Object number, Object a, Object b) {

        switch (descript) {
            case "Source":
                this.sourceNumber = number;
                this.countOfRequests = a;
                this.countOfFailure = b;
                break;
            case "Buffer":
                this.bufferNumber = number;
                this.bufferState = a;
                this.bufferRequest = b;
                break;
            case "Device":
                this.deviceNumber = number;
                this.deviceState = a;
                this.deviceRequest = b;
                break;
            default:
                break;
        }
    }


    public Object getSourceNumber() {
        return sourceNumber;
    }

    public Object getCountOfRequests() {
        return countOfRequests;
    }

    public Object getCountOfFailure() {
        return countOfFailure;
    }


    public Object getBufferNumber() {
        return bufferNumber;
    }

    public Object getBufferRequest() {
        return bufferRequest;
    }

    public Object getBufferState() {
        return bufferState;
    }

    public Object getDeviceNumber() {
        return deviceNumber;
    }

    public Object getDeviceState() {
        return deviceState;
    }

    public Object getDeviceRequest() {
        return deviceRequest;
    }
}
