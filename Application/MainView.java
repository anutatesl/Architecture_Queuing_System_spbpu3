package com.example.application.views.main;


import com.example.application.views.main.queuingSystem.project.Event;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.util.ArrayList;

@Route("")
public class MainView extends VerticalLayout {

    protected static ArrayList<Event> resEvents;
    protected static int countSources = 0;
    protected static int countBuffer = 0;
    protected static int countDevices = 0;

    protected static int step = -1;

    protected static TextFieldConstraints enterStep = new TextFieldConstraints("Enter the step: ", "");
    protected static VerticalLayout treeBottomLayout = new VerticalLayout();

    protected static AppLayoutNavbarPlacement layout = new AppLayoutNavbarPlacement();
    protected static TextArea implem = new TextArea();
    protected static TextField totalSteps = new TextField();

    protected static Grid<RowStepMode> grid21 = new Grid<>();
    protected static Grid<RowStepMode> grid22 = new Grid<>();
    protected static Grid<RowStepMode> grid23 = new Grid<>();

    public MainView() {

        HorizontalLayout  list1 = new HorizontalLayout();
        HorizontalLayout  list2 = new HorizontalLayout();
        VerticalLayout  list3 = new VerticalLayout();

        //Конфигурация системы
        list1.setSpacing(false);
        list1.setPadding(true);
        list1.setAlignItems(Alignment.STRETCH);

        VerticalLayout inputValForConfig = new VerticalLayout();
        inputValForConfig.setSpacing(false);
        inputValForConfig.setPadding(true);
        inputValForConfig.setAlignItems(Alignment.STRETCH);

        HorizontalLayout inputIndex = new HorizontalLayout();
        inputIndex.setSpacing(false);
        inputIndex.setPadding(true);
        inputIndex.setAlignItems(Alignment.STRETCH);
        inputIndex.getThemeList().add("spacing-xl");

        TextFieldConstraints countOfSources = new TextFieldConstraints("Enter count of sources:", "");
        TextFieldConstraints countOfDevices = new TextFieldConstraints("Enter count of devices:", "");
        TextFieldConstraints countOfRequests = new TextFieldConstraints("Enter count of requests:", "");
        TextFieldConstraints bufferSize = new TextFieldConstraints("Enter buffer size:", "");
        TextFieldConstraints alpha = new TextFieldConstraints("Enter alpha:", "for a uniform law of time distribution on sources");
        TextFieldConstraints beta = new TextFieldConstraints("Enter beta:", "for a uniform law of time distribution on sources");
        TextFieldConstraints lambda = new TextFieldConstraints("Enter lambda:", "for the exponential law of time distribution on devices");
        ButtonBasic bottom = new ButtonBasic("Configure", alpha, beta, lambda, countOfRequests,
                countOfSources, countOfDevices, bufferSize);

        inputIndex.add(alpha, beta, lambda);
        inputValForConfig.add(countOfSources, countOfDevices, countOfRequests, bufferSize, inputIndex, bottom);
        inputValForConfig.setAlignSelf(Alignment.CENTER, countOfSources, countOfDevices, countOfRequests, bufferSize, bottom);

        list1.add(inputValForConfig);

        layout.tabs.getTabAt(0).getElement().addEventListener("click", e -> {
            layout.setContent(list1);
        });


        //Конфигурация системы
        list2.setSpacing(false);
        list2.setPadding(true);
        list2.setAlignItems(Alignment.STRETCH);
        layout.tabs.getTabAt(1).getElement().addEventListener("click", e -> {
            layout.setContent(list2);
        });

        //Конфигурация системы
        list3.setSpacing(false);
        list3.setPadding(true);
        //list3.setAlignItems(Alignment.STRETCH);

        HorizontalLayout bottomsAndInfo = new HorizontalLayout();
        bottomsAndInfo.setSpacing(false);
        bottomsAndInfo.setPadding(true);
        //bottomsAndInfo.setAlignItems(Alignment.STRETCH);
        //bottomsAndInfo.getThemeList().add("spacing-xl");

        ButtonBasic bottom1 = new ButtonBasic("Go to step", list3);
        ButtonBasic bottom2 = new ButtonBasic("Back step", list3);
        ButtonBasic bottom3 = new ButtonBasic("Next step", list3);
        treeBottomLayout.add(bottom1, bottom2, bottom3);
        //twoBottomLayout.setAlignSelf(FlexComponent.Alignment.CENTER, bottom2, bottom3);

        implem.setWidth("600px");
        implem.setHeightFull();
        implem.setMinHeight("100px");
        implem.setMaxHeight("550px");
        implem.setReadOnly(true);
        implem.setLabel("Step-by-step information");

        bottomsAndInfo.add(treeBottomLayout, implem);

        list3.add(enterStep, bottomsAndInfo);

        grid21.addColumn(RowStepMode::getSourceNumber).setHeader("Source number").setAutoWidth(true);
        grid21.addColumn(RowStepMode::getCountOfRequests).setHeader("Count of requests").setAutoWidth(true);
        grid21.addColumn(RowStepMode::getCountOfFailure).setHeader("Count of failures").setAutoWidth(true);

        grid22.addColumn(RowStepMode::getBufferNumber).setHeader("Buffer cell number").setAutoWidth(true);
        grid22.addColumn(RowStepMode::getBufferState).setHeader("Buffer cell state").setAutoWidth(true);
        grid22.addColumn(RowStepMode::getBufferRequest).setHeader("Request number").setAutoWidth(true);

        grid23.addColumn(RowStepMode::getDeviceNumber).setHeader("Device number").setAutoWidth(true);
        grid23.addColumn(RowStepMode::getDeviceState).setHeader("Device state").setAutoWidth(true);
        grid23.addColumn(RowStepMode::getDeviceRequest).setHeader("Request number").setAutoWidth(true);

        layout.tabs.getTabAt(2).getElement().addEventListener("click", e -> {
            layout.setContent(list3);
        });

        layout.tabs.getTabAt(1).setEnabled(false);
        layout.tabs.getTabAt(2).setEnabled(false);

        add(layout);
    }
}