package com.wgapp.worksheetgenerator.Models;

import java.util.List;

public class Question {
    private int questionId;
    private String questionText;
    private List<Choice> choices;
    private String correctAnswerText;


    public Question(String questionText, List<Choice> choices, String correctAnswerText) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctAnswerText = correctAnswerText;
    }

    public Question() {

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

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getCorrectAnswerText() {
        return correctAnswerText;
    }

    public void setCorrectAnswerText(String answerText) {
        this.correctAnswerText = answerText;
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
