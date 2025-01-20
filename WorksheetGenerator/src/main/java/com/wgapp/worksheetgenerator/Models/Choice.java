package com.wgapp.worksheetgenerator.Models;

public class Choice {
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
}
