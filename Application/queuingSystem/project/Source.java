package com.example.application.views.main.queuingSystem.project;

import java.util.ArrayList;
import java.util.List;

public class Source {
    private int sourceNumber;
    private int countOfRequests;
    private int countOfAcceptedRequests;
    private double timeOfUsing;
    private double timeOfWaiting;

    private List<Double> bufferTimes = new ArrayList<>();
    private List<Double> serviceTimes = new ArrayList<>();

    public Source(int sourceNumber) {
        this.sourceNumber = sourceNumber;
        this.countOfRequests = 0;
        this.countOfAcceptedRequests = 0;
        this.timeOfUsing = 0;
        this.timeOfWaiting = 0;
    }

    public int getCountOfRequests() {
        return countOfRequests;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    void addNewRequest() { countOfRequests++; }

    void addAcceptedRequest() { countOfAcceptedRequests++; }

    void addTimeUse(double time) { timeOfUsing += time; }

    void addTimeWait(double time) { timeOfWaiting += time; }

    public double getProbabilityOfFailure() { return countOfAcceptedRequests * 1.0 / countOfRequests; }

    public double getAverageTimeUse() { return timeOfUsing / countOfRequests; }

    public double getAverageTimeWait() { return timeOfWaiting / countOfRequests; }

    public double getAverageServiceTime() { return getAverageTimeUse() - getAverageTimeWait(); }

    public void addBufferTimes(double time) {
        bufferTimes.add(time);
    }

    public void addServiceTimes(double time) {
        serviceTimes.add(time);
    }

    public double getDispersionTimeWait() {
        double sumOfSquares = 0;
        double avgTimeWait = getAverageTimeWait();
        for (int i = 0; i < countOfRequests; i++) {
            sumOfSquares += Math.pow(bufferTimes.get(i) - avgTimeWait, 2);
        }
        return sumOfSquares / countOfRequests;
    }

    public double getDispersionServiceTime() {

        double sumOfSquares = 0;
        double avgServiceTime = getAverageServiceTime();

        for (int i = 0; i < countOfRequests; i++) {
            sumOfSquares += Math.pow(serviceTimes.get(i) - avgServiceTime, 2);
        }

        return sumOfSquares / countOfRequests;
    }
}
