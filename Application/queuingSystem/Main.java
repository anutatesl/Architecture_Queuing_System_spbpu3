package com.example.application.views.main.queuingSystem;

import com.example.application.views.main.queuingSystem.project.Event;

import java.util.ArrayList;

public class Main {
    public static ArrayList<Event> configurate(ArrayList<String> inputVal) {
        //Simulation sim = new Simulation(15, 100, 0.001, 1000,3,4,6); 500 2 2
        //(15, 100, 0.001, 6000, 2, 3, 9)
        Simulation sim = new Simulation(Double.parseDouble(inputVal.get(0)), Double.parseDouble(inputVal.get(1)),
                Double.parseDouble(inputVal.get(2)), Integer.parseInt(inputVal.get(3)),
                Integer.parseInt(inputVal.get(4)), Integer.parseInt(inputVal.get(5)), Integer.parseInt(inputVal.get(6)));
        //sim.startAutoModeSimulation();
        sim.startStepByStepSimulation();
        return sim.getResEvents();
    }
}
