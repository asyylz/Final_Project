package com.wgapp.worksheetgenerator.DAO.Entities;

public class ChoiceEntity {
    private int choiceId;
    private String choiceText;

    public ChoiceEntity(String choiceText) {
        this.choiceText = choiceText;

    }

    public ChoiceEntity(int choiceId, String choiceText) {
        this.choiceId = choiceId;
        this.choiceText = choiceText;
    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    @Override
    public String toString() {
        return choiceText;
    }

    public void setChoiceId(int id) {
        choiceId = id;
    }

    public int getChoiceId() {
        return choiceId;
    }

    //    @Override
//    public String toString() {
//        // Return the question text and the options as a formatted string
//        StringBuilder sb = new StringBuilder();
//        sb.append("Question: ").append(questionText).append("\n");
//
//        for (Choice option : choices) {
//            sb.append(option).append("\n");  // This will use Option's toString() method
//        }
//
//        return sb.toString();
//    }
}
