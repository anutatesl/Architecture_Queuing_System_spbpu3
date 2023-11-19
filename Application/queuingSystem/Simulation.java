package com.example.application.views.main.queuingSystem;

import com.example.application.views.main.ButtonBasic;
import com.example.application.views.main.queuingSystem.project.*;
import com.example.application.views.main.ButtonBasic.*;

import java.util.ArrayList;

public class Simulation {
    private int countOfRequests;
    private Controller controller;
    private Generator generator;
    private DispatchInput dispatchInput;
    private Processor processor;
    private DispatchOutput dispatchOutput;
    public Buffer buffer;
    private int bufferSize;
    private int countOfSource;
    private ArrayList<Event> resEvents = new ArrayList<>();
    private double t = 1.643; //для 0.9 процента
    private double b = 0.1; //относительная точность
    public Simulation(double alpha, double beta, double lambda, int countOfRequests, int countOfSource, int countOfDevices,
                      int bufferSize) {
        this.countOfRequests = countOfRequests;
        this.bufferSize = bufferSize;
        this.countOfSource = countOfSource;
        buffer = new Buffer(bufferSize, countOfSource);
        dispatchInput = new DispatchInput(buffer);
        dispatchOutput = new DispatchOutput(buffer);
        generator = new Generator(countOfSource, dispatchInput);
        processor = new Processor(countOfDevices, dispatchOutput);
        controller = new Controller(alpha, beta, lambda, countOfRequests, generator, processor);
    }

    public void startStepByStepSimulation() {
        startModeling(countOfRequests);
        controller.showStepAutoMode();
        resEvents = Controller.getEvents();
        controller.clear();
    }

    public void startAutoModeSimulation() {
        startModeling(countOfRequests);
        int newCount = countOfRequests;
        int oldCount;
        double failureRate1 = controller.getFailureRate();
        controller.showStepAutoMode();
        double failureRate2 = 0;
        while (Math.abs(failureRate2 - failureRate1) >= failureRate2 * 0.1)
        {
            failureRate2 = failureRate1;
            oldCount = newCount;
            newCount = (int) (( t * t * (1 - failureRate2)) / (failureRate2 * b * b));

            controller.clear();
            buffer = new Buffer(bufferSize, countOfSource);

            startModeling(newCount);
            failureRate1 = controller.getFailureRate();
            System.out.println("num: " + oldCount);
        }
        System.out.println("the end: " + newCount);

        controller.showStepAutoMode();
    }

    public void startModeling(int countOfRequests) {
        controller.autoGenerateRequests(countOfRequests);
    }

    public ArrayList<Event> getResEvents() {
        return resEvents;
    }
}
