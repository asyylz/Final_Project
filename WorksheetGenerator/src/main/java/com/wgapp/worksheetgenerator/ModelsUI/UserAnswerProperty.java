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

    public String getAnswer() {
        return selectedChoice.get();
    }

    public void setAnswer(String answer) {
        this.selectedChoice.set(answer);
    }

    public int getQuestionIndex() {
        return questionIndex.get();
    }

    public void setQuestionIndex(int questionIndex) {
        this.questionIndex.set(questionIndex);
    }
}
