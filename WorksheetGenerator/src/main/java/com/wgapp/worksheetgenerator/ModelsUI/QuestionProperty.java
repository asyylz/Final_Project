package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class QuestionProperty {
    private  IntegerProperty questionId = new SimpleIntegerProperty();
    private  StringProperty questionText = new SimpleStringProperty();
    private  StringProperty correctAnswer = new SimpleStringProperty();
    private ListProperty<ChoiceProperty> choices = new SimpleListProperty<>(FXCollections.observableArrayList());


    public QuestionProperty(IntegerProperty questionId, StringProperty questionText, StringProperty correctAnswer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestionText() {
        return questionText.get();
    }


    public String getCorrectAnswer() {
        return correctAnswer.get();
    }

    public ListProperty<ChoiceProperty> getChoices() {
        return choices;
    }

    public void setChoices(ListProperty<ChoiceProperty> choices) {
        this.choices = choices;
    }
}
