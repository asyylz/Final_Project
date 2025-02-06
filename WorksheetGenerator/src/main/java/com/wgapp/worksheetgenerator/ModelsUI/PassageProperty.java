package com.wgapp.worksheetgenerator.ModelsUI;

import com.wgapp.worksheetgenerator.DAO.Entities.PassageEntity;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PassageProperty {
    private  IntegerProperty passageId = new SimpleIntegerProperty();
    private   StringProperty passageTitle = new SimpleStringProperty();
    private   StringProperty passageContent = new SimpleStringProperty();

    public PassageProperty(IntegerProperty passageId, StringProperty passageTitle, StringProperty passageContent) {
        this.passageId = passageId;
        this.passageTitle = passageTitle;
        this.passageContent = passageContent;
    }

    public PassageProperty(StringProperty passageTitle, StringProperty passageContent) {
        this.passageTitle = passageTitle;
        this.passageContent = passageContent;
    }

    public PassageProperty() {

    }

    public PassageProperty(PassageEntity passage) {
    }


    public int getPassageId() {
        return passageId.get();
    }

    public IntegerProperty passageIdProperty() {
        return passageId;
    }

    public void setPassageId(int passageId) {
        this.passageId.set(passageId);
    }

    public String getPassageTitle() {
        return passageTitle.get();
    }

    public StringProperty passageTitleProperty() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle.set(passageTitle);
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
