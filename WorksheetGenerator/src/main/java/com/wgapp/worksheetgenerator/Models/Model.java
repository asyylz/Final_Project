package com.wgapp.worksheetgenerator.Models;


import com.wgapp.worksheetgenerator.Views.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;

    // Initialize directly in the field declaration
    private ObjectProperty<MainSubjectOptions> mainSubject = new SimpleObjectProperty<>();
    private DifficultyLevelOptions difficultyLevel;
    private ObjectProperty<ISubSubjectOptions> subSubject = new SimpleObjectProperty<>();

    // Comprehension
    private final ListProperty<ComprehensionQuestionTypes> questionTypeList = new SimpleListProperty<>(FXCollections.observableArrayList()); // Empty list for question types

    // Passage
    private StringProperty passageContent = new SimpleStringProperty();
    private StringProperty passageTitle = new SimpleStringProperty();


    private Model() {
        this.viewFactory = new ViewFactory();
    }


    // Singleton pattern to ensure only one instance of Model
    public static synchronized Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    @Override
    public String toString() {
        return "Model{" +
                "mainSubject=" + mainSubject.get() +
                ", difficultyLevel=" + difficultyLevel +
                ", subSubject=" + subSubject.get() +
                ", questionTypeList=" + questionTypeList.get() +
                ", passageContent=" + passageContent +
                '}';
    }

    /*================================= GETTERS AND SETTER ===================================== */
    public ObjectProperty<MainSubjectOptions> getMainSubject() {
        return mainSubject;
    }

    public void setMainSubject(MainSubjectOptions mainSubject) {
        this.mainSubject.set(mainSubject);
    }

    public DifficultyLevelOptions getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevelOptions difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public ObjectProperty<ISubSubjectOptions> getSubSubject() {
        return subSubject;
    }


    public void setSubSubject(ISubSubjectOptions subSubject) {
        this.subSubject.set(subSubject);
    }

    public ObservableList<ComprehensionQuestionTypes> getQuestionTypeList() {
        return questionTypeList.get();
    }

    public ListProperty<ComprehensionQuestionTypes> questionTypeListProperty() {
        return questionTypeList;
    }

//    public void setQuestionTypeList(ComprehensionQuestionTypes[] questionTypeList) {
//        this.questionTypeList.set(questionTypeList);
//    }


    public StringProperty passageContentProperty() {
        return passageContent;
    }

    public void setPassageContent(String text) {
        this.passageContent.set(text);
    }

    public String getPassageContent() {
        return passageContent.get();
    }

    public String getPassageTitle() {
        return passageTitle.get();
    }

    public StringProperty passageTitleProperty() {
        return passageTitle;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle.set(passageTitle);
    }
}

