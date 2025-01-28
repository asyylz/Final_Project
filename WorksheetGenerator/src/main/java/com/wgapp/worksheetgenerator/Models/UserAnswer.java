package com.wgapp.worksheetgenerator.Models;

public class UserAnswer {
    private int questionId;
    private String answer;
    private int getQuestionIndex;

    public UserAnswer(String answer, int getQuestionIndex) {
        this.answer = answer;
        this.getQuestionIndex = getQuestionIndex;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionIndex() {
        return getQuestionIndex;
    }

    public void setQuestionIndex(int getQuestionIndex) {
        this.getQuestionIndex = getQuestionIndex;
    }

}
