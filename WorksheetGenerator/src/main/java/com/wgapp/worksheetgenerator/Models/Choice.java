package com.wgapp.worksheetgenerator.Models;

public class Choice {
    private String optionText;
    private boolean isCorrectAnswer;

    public Choice(String optionText ) {
        this.optionText = optionText;

    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public boolean isCorrectAnswer() {
        return isCorrectAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        isCorrectAnswer = correctAnswer;
    }
}
