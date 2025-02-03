package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;

public class UserAnswerProperty {

    private  IntegerProperty questionId = new SimpleIntegerProperty();
    private  StringProperty selectedChoice = new SimpleStringProperty();
    private  IntegerProperty questionIndex = new SimpleIntegerProperty();

    public UserAnswerProperty(String selectedChoice, Integer questionIndex) {
        this.selectedChoice.set(selectedChoice);
        this.questionIndex.set(questionIndex);
    }

    public int getQuestionId() {
        return questionId.get();
    }

    public IntegerProperty questionIdProperty() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId.set(questionId);
    }

    public String getAnswer() {
        return selectedChoice.get();
    }

    public StringProperty answerProperty() {
        return selectedChoice;
    }

    public void setAnswer(String answer) {
        this.selectedChoice.set(answer);
    }

    public int getQuestionIndex() {
        return questionIndex.get();
    }

    public IntegerProperty questionIndexProperty() {
        return questionIndex;
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex.set(questionIndex);
    }
}
