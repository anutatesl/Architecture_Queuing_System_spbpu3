package com.example.application.views.main.queuingSystem.project;

public class Event {
    private EventType type;
    private String requestId;
    private String information;

    public Event(EventType type, String requestId, String information) {
        this.type = type;
        this.requestId = requestId;
        this.information = information;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getInformation() { return information; }
}