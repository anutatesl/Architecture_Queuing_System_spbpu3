package com.example.application.views.main;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("text-field-constraints")
public class TextFieldConstraints extends HorizontalLayout {

    public TextField field = new TextField();

    public TextFieldConstraints(String str1, String str2) {
        setPadding(false);
        field.setLabel(str1);
        field.setRequiredIndicatorVisible(true);
        field.setAllowedCharPattern("[0-9.]");
        field.setWidth("350px");
        field.setHelperText(str2);
        add(field);
    }

    public String returnValue() {
        return field.getValue();
    }
}
