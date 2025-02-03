package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;
import javafx.collections.FXCollections;

public class QuestionProperty {
    private  IntegerProperty questionId = new SimpleIntegerProperty();
    private  StringProperty questionText = new SimpleStringProperty();
    private  StringProperty correctAnswer = new SimpleStringProperty();
    private ListProperty<ChoiceProperty> choices = new SimpleListProperty<>(FXCollections.observableArrayList());

    public QuestionProperty(IntegerProperty questionId, StringProperty questionText, StringProperty correctAnswer, ListProperty<ChoiceProperty> choices) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.choices = choices;
    }

    public QuestionProperty(IntegerProperty questionId, StringProperty questionText, StringProperty correctAnswer) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
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

    public String getQuestionText() {
        return questionText.get();
    }

    public StringProperty questionTextProperty() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText.set(questionText);
    }

    public String getCorrectAnswer() {
        return correctAnswer.get();
    }

    public StringProperty correctAnswerProperty() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer.set(correctAnswer);
    }

    public ListProperty<ChoiceProperty> getChoices() {
        return choices;
    }

    public void setChoices(ListProperty<ChoiceProperty> choices) {
        this.choices = choices;
    }
}
