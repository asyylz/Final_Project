package com.wgapp.worksheetgenerator.Models;

public class Choice {
    private int choiceId;
    private String choiceText;
    private boolean isCorrectAnswer;

    public Choice(String choiceText) {
        this.choiceText = choiceText;

    }

    public String getChoiceText() {
        return choiceText;
    }

    public void setChoiceText(String choiceText) {
        this.choiceText = choiceText;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
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
