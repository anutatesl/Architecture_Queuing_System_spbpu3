package com.example.application.views.main;

import com.example.application.views.main.queuingSystem.project.Device;
import com.example.application.views.main.queuingSystem.project.Source;

import java.util.ArrayList;
import java.util.List;

public class GridAutomode {
    ArrayList<Source> outSources;
    ArrayList<Device> outDevices;
    private double outTime;

    static List<RowAutomode> rows1 = new ArrayList<>();
    static List<RowAutomode> rows2 = new ArrayList<>();
    public GridAutomode(ArrayList<Source> sources, ArrayList<Device> devices, double time) {
        this.outSources = sources;
        this.outDevices = devices;
        this.outTime = time;
        f1();
        f2();
    }

    public void f1() {
        int size = outSources.size();
        Object[][] mass = new String[size][8];
        for (int i = 0; i < size; i++) {

            mass[i][0] = Double.toString(outSources.get(i).getSourceNumber() + 1);
            mass[i][1] = Double.toString(outSources.get(i).getCountOfRequests());
            mass[i][2] = Double.toString(makeShorter(outSources.get(i).getProbabilityOfFailure()));
            mass[i][3] = Double.toString(makeShorter(outSources.get(i).getAverageTimeUse()));
            mass[i][4] = Double.toString(makeShorter(outSources.get(i).getAverageTimeWait()));
            mass[i][5] = Double.toString(makeShorter(outSources.get(i).getAverageServiceTime()));
            mass[i][6] = Double.toString(makeShorter(outSources.get(i).getDispersionTimeWait()));
            mass[i][7] = Double.toString(makeShorter(outSources.get(i).getDispersionServiceTime()));

            rows1.add(new RowAutomode(mass[i][0], mass[i][1], mass[i][2], mass[i][3], mass[i][4], mass[i][5], mass[i][6], mass[i][7]));
        }
    }
    public void f2() {
        int size = outDevices.size();
        Object[][] mass = new String[size][2];
        for (int i = 0; i < size; i++) {
            mass[i][0] = Double.toString(outDevices.get(i).getDeviceNumber() + 1);
            mass[i][1] = Double.toString(makeShorter(outDevices.get(i).getBusyTime() / outTime));
            rows2.add(new RowAutomode(mass[i][0], mass[i][1]));
        }
    }

    public static List<RowAutomode> getRows1() {
        return rows1;
    }
    public static List<RowAutomode> getRows2() {
        return rows2;
    }

    public static void clearRows() {
        rows1.clear();
        rows2.clear();
    }

    public double makeShorter(double num) {
        String formattedDouble = String.format("%.2f", num);
        return Double.parseDouble(formattedDouble.replace(",", "."));
    }
}
