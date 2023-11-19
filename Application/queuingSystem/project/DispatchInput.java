package com.example.application.views.main.queuingSystem.project;

import static com.example.application.views.main.queuingSystem.project.Controller.events;
import static com.example.application.views.main.queuingSystem.project.Generator.sources;

public class DispatchInput {
    private final Buffer buffer;

    public DispatchInput (Buffer buffer) {
        this.buffer = buffer;
    }

    public Request refuseRequest(int sourceNumber) {
        return buffer.getEarliestRequest(sourceNumber);
    }

    public int addInBuff(Request request) {
        int numberSourceAccepted = -1;
        if (!buffer.addRequest(request)) {
            Request requestRefuse =  refuseRequest(request.getSourceNumber());
            buffer.addRequest(request);
            events.add(new Event(EventType.REQUEST_REFUSE, requestRefuse.getTaskId(), "Заявке " + requestRefuse.getTaskId()
                    + " было отказано в обслуживании и она покинула в буфер " + "999"));
            sources.get(requestRefuse.getSourceNumber() - 1).addBufferTimes(request.getTimeOfMaking() - requestRefuse.getTimeOfMaking());
            sources.get(requestRefuse.getSourceNumber() - 1).addServiceTimes(0.0);
            sources.get(requestRefuse.getSourceNumber() - 1).addTimeUse(request.getTimeOfMaking() - requestRefuse.getTimeOfMaking());
            sources.get(requestRefuse.getSourceNumber() - 1).addTimeWait(request.getTimeOfMaking() - requestRefuse.getTimeOfMaking());
            numberSourceAccepted = requestRefuse.getSourceNumber();
        }
        events.add(new Event(EventType.REQUEST_BUFFER, request.getTaskId(), "Заявка " + request.getTaskId()
                + " была отправлена в буффер на место " + buffer.getRequestPosition(request)));
        return numberSourceAccepted;
    }
}