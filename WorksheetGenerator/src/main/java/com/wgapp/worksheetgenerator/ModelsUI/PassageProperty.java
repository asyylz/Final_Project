package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassageProperty {
    private  IntegerProperty passageId = new SimpleIntegerProperty();
    private   StringProperty passageTitle = new SimpleStringProperty();
    private   StringProperty passageContent = new SimpleStringProperty();


    public PassageProperty(StringProperty passageTitle, StringProperty passageContent) {
        this.passageTitle = passageTitle;
        this.passageContent = passageContent;
    }

    public PassageProperty() {

    }


    public String getPassageTitle() {
        return passageTitle.get();
    }

    public StringProperty passageTitleProperty() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle.set(passageTitle.toUpperCase());
    }

    public String getPassageContent() {
        return passageContent.get();
    }

    public StringProperty passageContentProperty() {
        return passageContent;
    }

    public void setPassageContent(String passageContent) {
        this.passageContent.set(passageContent);
    }
}
