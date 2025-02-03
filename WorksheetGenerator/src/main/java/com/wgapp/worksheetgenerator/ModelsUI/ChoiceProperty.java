package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChoiceProperty {
    private  IntegerProperty choiceId = new SimpleIntegerProperty();
    private  StringProperty choiceText = new SimpleStringProperty();

    public ChoiceProperty(IntegerProperty choiceId, StringProperty choiceText) {
        this.choiceId = choiceId;
        this.choiceText = choiceText;
    }

    public int getChoiceId() {
        return choiceId.get();
    }

    public IntegerProperty choiceIdProperty() {
        return choiceId;
    }

    public void setChoiceId(int choiceId) {
        this.choiceId.set(choiceId);
    }

    public String getChoiceText() {
        return choiceText.get();
    }

    public StringProperty choiceTextProperty() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText.set(choiceText);
    }
}
