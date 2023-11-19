package com.example.application.views.main;

import com.example.application.views.main.queuingSystem.project.Event;
import com.example.application.views.main.queuingSystem.project.EventType;

import java.util.ArrayList;
import java.util.List;

public class GridStepmode {
    ArrayList<Event> outEvents;

    static List<RowStepMode> rows1 = new ArrayList<>();
    static List<RowStepMode> rows2 = new ArrayList<>();
    static List<RowStepMode> rows3 = new ArrayList<>();


    public GridStepmode(int sources, int devices, int buffer, ArrayList<Event> events) {
        this.outEvents = events;
        f1(sources);
        f2(buffer, sources);
        f3(devices);
    }

    public void f1(int sources) {
        Object[][] mass = new String[sources][3];
        for (int i = 0; i < sources; i++) {

            mass[i][0] = Integer.toString(i+1);
            mass[i][1] = Integer.toString(0);
            mass[i][2] = Integer.toString(0);
        }
        for (int j = MainView.step; j >= 0; j-- ) {
            Event curEv = MainView.resEvents.get(j);
            int curNum = getNumInEvent(curEv);
            if (curEv.getType() == EventType.REQUEST_GENERATION) {
                mass[curNum - 1][1] = Integer.toString(Integer.parseInt((String) mass[curNum - 1][1]) + 1);
            } else if (curEv.getType() == EventType.REQUEST_REFUSE) {
                String string = curEv.getInformation();
                int i = string.lastIndexOf(".");
                string = string.substring(i-1, i);
                mass[Integer.parseInt(string) - 1][2] = Integer.toString(Integer.parseInt((String) mass[Integer.parseInt(string) - 1][1]) + 1);
            }
        }
        for (int i = 0; i < sources; i++) {

            rows1.add(new RowStepMode("Source" ,mass[i][0], mass[i][1], mass[i][2]));
        }

    }

    public void f2(int buffer, int sources) {
        Object[][] mass = new String[buffer][3];
        int remainder = buffer % sources;
        int countBlocks = sources;
        int blockSize = buffer / sources;
        int allCell = 0;
        for (int i = 0; i < countBlocks; i++) {
            for (int j = 0; j < blockSize; j++) {
                int indBl = i + 1;
                int indPlBl = j + 1;
                mass[allCell][0] = Integer.toString(indBl) + '.' + Integer.toString(indPlBl);
                mass[allCell][1] = "Free";
                mass[allCell][2] = " ";
                allCell++;
            }
        }
        for (int j = 0; j < remainder; j++) {
            mass[buffer - 1][0] = Integer.toString(countBlocks) + '.' + (blockSize + j + 1);
            mass[buffer - 1][1] = "Free";
            mass[buffer - 1][2] = " ";
        }
        for (int i = 0; i <= MainView.step; i++) {
            Event curEv = MainView.resEvents.get(i);
            int curNum = getNumInEvent(curEv);
            if (curEv.getType() == EventType.REQUEST_BUFFER) {
                String string = curEv.getInformation();
                int j = string.indexOf(".");  //.lastIndexOf(".");
                String string1 = string.substring(j - 1, j + 2);
                String string2 = string.substring(j - 1, j);
                int ind = (Integer.parseInt(string2) - 1) * blockSize + curNum - 1;
                mass[ind][1] = "Busy";
                mass[ind][2] = string1;
            } else if (curEv.getType() == EventType.REQUEST_REFUSE) {
                String string = curEv.getInformation();
                int j = string.indexOf(".");  //.lastIndexOf(".");
                String string1 = string.substring(j - 1, j + 2);
                for (int k = 0; k < buffer; k++) {
                    if (mass[k][2].equals(string1)) {
                        mass[k][1] = "Free";
                        mass[k][2] = " ";
                    }
                }
            } else if (curEv.getType() == EventType.REQUEST_CHOICE_BUF) {
                String string = curEv.getInformation();
                int j = string.indexOf(".");  //.lastIndexOf(".");
                String string1 = string.substring(j - 1, j + 2);
                for (int k = 0; k < buffer; k++) {
                    if (mass[k][2].equals(string1)) {
                        mass[k][1] = "Free";
                        mass[k][2] = " ";
                    }
                }
            }
        }
        for (int i = 0; i < buffer; i++) {
            rows2.add(new RowStepMode("Buffer" ,mass[i][0], mass[i][1], mass[i][2]));
        }
    }

    public void f3(int devices) {
        Object[][] mass = new String[devices][3];
        for (int i = 0; i < devices; i++) {
            mass[i][0] = Integer.toString(i+1);
            mass[i][1] = "Free";
            mass[i][2] = " ";
        }
        mass[0][0] = Integer.toString(1)+" Y";
        for (int i = 0; i <= MainView.step; i++) {
            Event curEv = MainView.resEvents.get(i);
            int curNum = getNumInEvent(curEv);
            if (curEv.getType() == EventType.REQUEST_CHOICE_DEVICE) {
                String string = curEv.getInformation();
                int j = string.indexOf(".");  //.lastIndexOf(".");
                String string1 = string.substring(j - 1, j + 2);
                mass[curNum - 1][1] = "Busy";
                mass[curNum - 1][2] = string1;

                for (int k = 0; k < devices; k++) {
                    mass[k][0] = Integer.toString(k+1);
                }
                if (curNum == devices) {
                    mass[0][0] = Integer.toString(1)+" Y";
                } else {
                    mass[curNum][0] = Integer.toString(curNum + 1)+" Y";
                }

            } else if (curEv.getType() == EventType.REQUEST_FINISH_DEVICE) {
                mass[curNum - 1][1] = "Free";
                mass[curNum - 1][2] = " ";
            }
        }
        for (int i = 0; i < devices; i++) {
            rows3.add(new RowStepMode("Device" ,mass[i][0], mass[i][1], mass[i][2]));
        }
    }

    public static List<RowStepMode> getRows1() { return rows1; }
    public static List<RowStepMode> getRows2() {
        return rows2;
    }

    public static List<RowStepMode> getRows3() {
        return rows3;
    }

    public static void clearRows() {
        rows1.clear();
        rows2.clear();
        rows3.clear();
    }

    public int getNumInEvent(Event event) {
        String string = event.getInformation();
        int i = string.lastIndexOf(" ") + 1;
        string = string.substring(i);
        return Integer.parseInt(string);
    }
}
