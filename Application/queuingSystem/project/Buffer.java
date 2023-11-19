package com.example.application.views.main.queuingSystem.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Buffer {
    private ArrayList<ArrayList<Request>> arrayAllRequests = new ArrayList<ArrayList<Request>>();
    private int size;
    private int occupiedSize;
    private Map<Integer, ArrayList<Request>> blocks = new HashMap<>();
    private int blockSize;
    private int countOfBlocks;
    private int remainder;

    public Buffer(int newSize, int countOfSources) {
        this.size = newSize;
        this.occupiedSize = 0;
        this.countOfBlocks = countOfSources;
        this.blockSize = newSize / countOfSources;
        remainder = newSize % countOfSources;
        for (int i = 0; i < countOfSources; i++) {
            ArrayList<Request> arrayReq = new ArrayList<>();
            arrayAllRequests.add(arrayReq);
        }
    }

    public Request getEarliestRequest(int sourceNumber) {
        Request currTask = arrayAllRequests.get(sourceNumber - 1).get(arrayAllRequests.get(sourceNumber - 1).size()-1);
        //deleteRequest(currTask);
        arrayAllRequests.get(currTask.getSourceNumber() - 1).remove(arrayAllRequests.get(sourceNumber - 1).size()-1);
        occupiedSize -= 1;
        return currTask;
    }

    public boolean addRequest(Request request) {
        int numSource = request.getSourceNumber();
        if (isFull(numSource))
        {
            return false;
        }
        arrayAllRequests.get(numSource - 1).add(request);
        blocks.put(numSource, arrayAllRequests.get(numSource - 1));
        occupiedSize += 1;
        return true;
    }

    public Request getOutRequest() {
        int numBlock = 0;
        for (int i = 0; i < countOfBlocks; i++) {
            if (arrayAllRequests.get(i).isEmpty()) {
                continue;
            } else {
                numBlock = i + 1;
                break;
            }
        }
        return this.arrayAllRequests.get(numBlock - 1).get(0);
    }

    public void deleteRequest(Request request) {
        arrayAllRequests.get(request.getSourceNumber() - 1).remove(0);
        occupiedSize -= 1;
    }

    public boolean isEmpty() {
        return occupiedSize == 0;
    }

    public boolean isFull(int numBlock) {
        if (numBlock == countOfBlocks) {
            return arrayAllRequests.get(numBlock - 1).size() == blockSize + remainder;
        }
        return arrayAllRequests.get(numBlock - 1).size() == blockSize;
    }

    public String getRequestPosition(Request request) {
        int ind = arrayAllRequests.get(request.getSourceNumber() - 1).indexOf(request) + 1;
        return Integer.toString(request.getSourceNumber()) + " . " + (arrayAllRequests.get(request.getSourceNumber() - 1).indexOf(request) + 1);
    }
}
