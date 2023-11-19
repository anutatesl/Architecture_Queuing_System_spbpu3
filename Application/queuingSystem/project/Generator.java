package com.example.application.views.main.queuingSystem.project;

import java.util.ArrayList;
import java.util.Random;

import static com.example.application.views.main.queuingSystem.project.Controller.events;

public class Generator {
    private static int countOfSource;
    protected static ArrayList<Source> sources;
    private static DispatchInput dispatchInput;

    public Generator(int countOfSource, DispatchInput dispatchInput) {
        Generator.countOfSource = countOfSource;
        Generator.dispatchInput = dispatchInput;
        sources = new ArrayList<>();
        for (int i = 0; i < countOfSource; i++) {
            sources.add(new Source(i));
        }
    }

    public static ArrayList<Source> getSources() {
        return sources;
    }

    public static int getCountOfSource() {
        return countOfSource;
    }

    public void startGenerating(double generationTime, int currReq) {
        int currentSourceNumber = getMinWorkload();
        Source currSource = sources.get(currentSourceNumber - 1);
        currSource.addNewRequest();
        Request newRequest = new Request(currSource.getCountOfRequests(), currSource.getSourceNumber() + 1, generationTime, 0);
        events.add(new Event(EventType.REQUEST_GENERATION, newRequest.getTaskId(), "Заявка " + (currReq + 1)
                + " была сгенерированна прибором под номером " + (currSource.getSourceNumber() + 1)));
        int numberAcceptedSource = dispatchInput.addInBuff(newRequest);
        if (numberAcceptedSource != -1) {sources.get(numberAcceptedSource - 1).addAcceptedRequest();}
    }

    // Находим менее загруженный источник
    public static int getMinWorkload() {
        int minValue = 1000000;
        Random random = new Random();
        ArrayList<Integer> arrayIndexSource = new ArrayList<>();
        int countMinSources = 0;
        for (Source source : sources) {
            int curr = source.getCountOfRequests();
            if (curr <= minValue) {
                if (curr < minValue) {
                    arrayIndexSource.clear();
                    countMinSources = 0;
                }
                countMinSources++;
                minValue = curr;
                arrayIndexSource.add(source.getSourceNumber());
            }
        }
        int minSource = arrayIndexSource.get(random.nextInt(countMinSources));
        return minSource + 1;
    }

    // Процент отказа источника
    public double getAcceptedRequestsOfSources() {
        double sum = 0;
        for (Source curSou : sources) {
            sum += curSou.getProbabilityOfFailure();
        }
        return sum / getCountOfSource();
    }

    // Обновляем источники для нового шага автоматического режима
    public void updateSource() {
        for (int i = 0; i < sources.size(); i++) {
            sources.set(i, new Source(i));
        }
    }
}
