package com.wgapp.worksheetgenerator.DAO.Entities;

import java.util.List;

public class QuestionEntity {
    private int questionId;
    private String questionText;
    private List<ChoiceEntity> choiceEntities;
    private String correctAnswerText;


    public QuestionEntity(String questionText, List<ChoiceEntity> choiceEntities, String correctAnswerText) {
        this.questionText = questionText;
        this.choiceEntities = choiceEntities;
        this.correctAnswerText = correctAnswerText;
    }

    public QuestionEntity() {

    }


    // Getters and Setters
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<ChoiceEntity> getChoices() {
        return choiceEntities;
    }

    public void setChoices(List<ChoiceEntity> options) {
        this.choiceEntities = options;
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

        for (ChoiceEntity option : choiceEntities) {
            sb.append(option).append("\n");  // This will use Option's toString() method
        }

        return sb.toString();
    }
}
