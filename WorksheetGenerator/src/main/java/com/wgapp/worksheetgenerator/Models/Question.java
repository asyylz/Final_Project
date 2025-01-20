package com.wgapp.worksheetgenerator.Models;

import java.util.List;

public class Question {
    private String questionText;
    private List<Choice> choices;

    public Question(String questionText, List<Choice> choices) {
        this.questionText = questionText;
        this.choices = choices;
    }

    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> options) {
        this.choices = options;
    }

    @Override
    public String toString() {
        // Return the question text and the options as a formatted string
        StringBuilder sb = new StringBuilder();
        sb.append("Question: ").append(questionText).append("\n");

        for (Choice option : choices) {
            sb.append(option).append("\n");  // This will use Option's toString() method
        }

        return sb.toString();
    }
}
