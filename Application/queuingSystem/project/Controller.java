package com.example.application.views.main.queuingSystem.project;

import com.example.application.views.main.GridAutomode;

import java.util.ArrayList;

public class Controller {
    private static double alpha;
    private static double beta;
    private static double lambda;
    private static double workingTime = 0;
    private static int currentRequests; // Текущее количество заявок (готовых + генерирующихся).
    private static int requiredRequests = 0; // Необходимое в симуляции количество заявок.
    protected static ArrayList<Event> events;
    private Generator generator;
    private Processor processor;

    public Controller(double alpha, double beta, double lambda, int requiredRequests, Generator generator, Processor processor) {
        Controller.alpha = alpha;
        Controller.beta = beta;
        Controller.lambda = lambda;
        Controller.requiredRequests = requiredRequests;
        events = new ArrayList<>();
        this.generator = generator;
        this.processor = processor;
    }

    public static double getWorkingTime() {
        return workingTime;
    }

    public static void setWorkingTime(double workingTime) {
        Controller.workingTime = workingTime;
    }

    public static double getAlpha() {
        return alpha;
    }

    public static double getBeta() {
        return beta;
    }

    public static double getLambda() {
        return lambda;
    }

    public void autoGenerateRequests(int countOfRequests) {
        currentRequests= 0;
        double nextGenerationTime = 0.1;
        while (currentRequests < countOfRequests) {
            setWorkingTime(nextGenerationTime);

            processor.fillFreeDevices(nextGenerationTime);
            processor.checkNextStep(nextGenerationTime);

            generator.startGenerating(nextGenerationTime, currentRequests);
            processor.startProcessing(nextGenerationTime);
            currentRequests++;
            nextGenerationTime = getNextGenerationTime();
        }
        completeWork(nextGenerationTime);
    }

    public void completeWork (double currTime) {
        while (true) {
            processor.fillFreeDevices(currTime);
            processor.finishWork(currTime);
            if (processor.isAllDevicesFinish()) {
                break;
            }
            setWorkingTime(currTime);
            currTime = getNextGenerationTime();
        }
    }

    public double getFailureRate() {
        return generator.getAcceptedRequestsOfSources();
    }

    public void clear() {
        generator.updateSource();
        processor.updateDevices();
        //events.clear();
    }

    public static double getNextGenerationTime() { return getWorkingTime() + getUniformDistribution(getAlpha(), getBeta()); }

    public static double getNextReleaseTime() {
        return getWorkingTime() + getExponentialDistribution(getLambda());
    }

    public static double getUniformDistribution(double a, double b) {
        return Math.random() * (b - a) + a;
    }

    public static double getExponentialDistribution(double lambda) {
        return -(1 / lambda) * Math.log(1 - Math.random());
    }
    public void showStepAutoMode() {
        new GridAutomode(Generator.getSources(), Processor.getDevices(), workingTime);
    }

    public static ArrayList<Event> getEvents() {
        return events;
    }
}