package com.example.application.views.main;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.example.application.views.main.queuingSystem.Main;

import java.util.List;
import java.util.ArrayList;

import static com.example.application.views.main.MainView.*;
import static com.example.application.views.main.queuingSystem.project.EventType.REQUEST_CHOICE_BUF;
import static com.example.application.views.main.queuingSystem.project.EventType.REQUEST_REFUSE;

@Route("button-basic")
public class ButtonBasic extends Div {
    public ButtonBasic(String descript, TextFieldConstraints alpha, TextFieldConstraints beta,
                       TextFieldConstraints lambda, TextFieldConstraints countOfRequests,
                       TextFieldConstraints countOfSources, TextFieldConstraints countOfDevices, TextFieldConstraints bufferSize) {
        Button button = new Button(descript);
        button.addClickListener(clickEvent -> {

            HorizontalLayout  list2 = new HorizontalLayout();
            list2.setSpacing(false);
            list2.setPadding(true);
            list2.setAlignItems(FlexComponent.Alignment.STRETCH);
            MainView.layout.tabs.getTabAt(1).getElement().addEventListener("click", e -> {
                MainView.layout.setContent(list2);
            });

            GridAutomode.clearRows();
            ArrayList<String> inputVal = new ArrayList<String>();
            inputVal.add(alpha.returnValue());
            inputVal.add(beta.returnValue());
            inputVal.add(lambda.returnValue());
            inputVal.add(countOfRequests.returnValue());
            inputVal.add(countOfSources.returnValue());
            MainView.countSources = Integer.parseInt(countOfSources.returnValue());
            inputVal.add(countOfDevices.returnValue());
            MainView.countDevices = Integer.parseInt(countOfDevices.returnValue());
            inputVal.add(bufferSize.returnValue());
            MainView.countBuffer = Integer.parseInt(bufferSize.returnValue());
            MainView.resEvents = Main.configurate(inputVal);

            totalSteps.setReadOnly(true);
            totalSteps.setWidth("350px");
            totalSteps.setValue("Total number of steps: " + MainView.resEvents.size());
            treeBottomLayout.add(totalSteps);;

            MainView.layout.tabs.getTabAt(1).setEnabled(true);
            MainView.layout.tabs.getTabAt(2).setEnabled(true);

            VerticalLayout twoTableAuto = new VerticalLayout();
            twoTableAuto.setSpacing(false);
            twoTableAuto.setPadding(true);
            twoTableAuto.setAlignItems(FlexComponent.Alignment.STRETCH);
            twoTableAuto.getThemeList().add("spacing-xl");
            List<RowAutomode> rows1 = GridAutomode.getRows1();
            Grid<RowAutomode> grid1 = new Grid<>();
            grid1.setItems(rows1);
            grid1.setAllRowsVisible(true);
            grid1.addColumn(RowAutomode::getSourceNumber).setHeader("Source number").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getCountOfRequests).setHeader("Count of requests").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getProbabilityOfFailure).setHeader("Probability of failure").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getAverageTimeUse).setHeader("Average time use").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getAverageTimeWait).setHeader("Average time wait").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getAverageServiceTime).setHeader("Average service time").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getDispersionTimeWait).setHeader("Dispersion time wait").setAutoWidth(true);
            grid1.addColumn(RowAutomode::getDispersionServiceTimeUse).setHeader("Dispersion service time").setAutoWidth(true);
            List<RowAutomode> rows2 = GridAutomode.getRows2();
            Grid<RowAutomode> grid2 = new Grid<>();
            grid2.setItems(rows2);
            grid2.setAllRowsVisible(true);
            grid2.addColumn(RowAutomode::getDeviceNumber).setHeader("Device number");
            grid2.addColumn(RowAutomode::getCoefOfUse).setHeader("Сoefficient of use");
            twoTableAuto.add(grid1, grid2);
            list2.add(twoTableAuto);
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(button);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(horizontalLayout);
    }

    public ButtonBasic(String descript, VerticalLayout list) {
        Button button = new Button(descript);

        button.addClickListener(clickEvent -> {
            switch (descript) {
                case "Go to step":
                    String numStep = enterStep.returnValue();
                    if (resEvents.size() >= Integer.parseInt(numStep) && Integer.parseInt(numStep) >= 1) {
                        MainView.step = Integer.parseInt(numStep);
                        MainView.step -= 1;
                        calculateStep(list);
                        showStep(list);
                    }
                    break;
                case "Back step":
                    if (MainView.step >= 1) {
                        MainView.step -= 1;
                        calculateStep(list);
                        showStep(list);
                    }
                    break;
                case "Next step":
                    if (MainView.step < resEvents.size() - 1) {
                        MainView.step += 1;
                        calculateStep(list);
                        showStep(list);
                    }
                    break;
                default:
                    break;
            }
        });

        HorizontalLayout horizontalLayout = new HorizontalLayout(button);
        horizontalLayout.setAlignItems(FlexComponent.Alignment.BASELINE);
        add(horizontalLayout);
    }
    private void showStep(VerticalLayout list) {
        HorizontalLayout threeTableStep = new HorizontalLayout();

        List<RowStepMode> rows1 = GridStepmode.getRows1();
        grid21.setItems(rows1);
        grid21.setAllRowsVisible(true);

        List<RowStepMode> rows2 = GridStepmode.getRows2();
        grid22.setItems(rows2);
        grid22.setAllRowsVisible(true);

        List<RowStepMode> rows3 = GridStepmode.getRows3();
        grid23.setItems(rows3);
        grid23.setAllRowsVisible(true);

        threeTableStep.add(grid21, grid22, grid23);
        list.add(threeTableStep);
    }

    public void calculateStep(VerticalLayout list) {
        GridStepmode.clearRows();
        implem.setValue("Сurrent step: "+ (MainView.step + 1));

        String string = MainView.resEvents.get(MainView.step).getInformation();
        if (MainView.resEvents.get(MainView.step).getType() == REQUEST_REFUSE || MainView.resEvents.get(MainView.step).getType() == REQUEST_CHOICE_BUF) {
            int i = string.lastIndexOf(" ");
            string = string.substring(0, i);
        }
        implem.setValue("Сurrent step: "+ (MainView.step + 1) + '\n' + string);

        new GridStepmode(MainView.countSources, MainView.countDevices, MainView.countBuffer, MainView.resEvents);
    }
}

