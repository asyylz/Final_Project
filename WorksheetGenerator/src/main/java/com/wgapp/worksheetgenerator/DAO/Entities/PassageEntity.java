package com.wgapp.worksheetgenerator.DAO.Entities;

public class PassageEntity {
    Integer passageId; //The Integer type can hold null values, making it suitable for scenarios where the ID is generated later.
    String passageTitle;
    String passageText;

    public PassageEntity(String passageTitle, String passageText) {
        this.passageId = null;
        this.passageTitle = passageTitle;
        this.passageText = passageText;
    }
    public PassageEntity( ) {

    }

    public int getPassageId() {
        return passageId;
    }

    public void setPassageId(int passageId) {
        this.passageId = passageId;
    }

    public String getPassageTitle() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle = passageTitle;
    }

    public String getPassageText() {
        return passageText;
    }

    public void setPassageText(String passageText) {
        this.passageText = passageText;
    }
}
