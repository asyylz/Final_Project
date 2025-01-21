package com.wgapp.worksheetgenerator.Models;


import com.wgapp.worksheetgenerator.Views.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    private static Model model;
    // Fields to store the data for subject, sub-subject, and other settings
    // Initialize directly in the field declaration
    private ObjectProperty<MainSubjectOptions> mainSubject = new SimpleObjectProperty<>(); // Default subject
    private DifficultyLevelOptions difficultyLevel ; // Default difficulty level
    // private ObjectProperty<ISubSubjectOptions> subSubject;
    // Observable list to hold multiple selected question types
    private ObjectProperty<ISubSubjectOptions> subSubject = new SimpleObjectProperty<>(); // Default sub-subject
    private final ListProperty<ComprehensionQuestionTypes> questionTypeList = new SimpleListProperty<>(FXCollections.observableArrayList()); // Empty list for question types
    private StringProperty passageContent = new SimpleStringProperty();
    private final ViewFactory viewFactory;


    private Model() {
        this.viewFactory = new ViewFactory();
    }

    // Singleton pattern to ensure only one instance of Model
//    private Model() {
//        this.mainSubject = MainSubjectOptions.ENGLISH; // Default subject
//        this.subSubject = new SimpleObjectProperty<>(SubSubjectOptionsEnglish.COMPREHENSION); // Default sub-subject
//        this.schoolYear = SchoolYearOptions.YEAR1; // Default school year
//        this.difficultyLevel = DifficultyLevelOptions.Grade1; // Default difficulty level
//        this.questionTypeList = new SimpleListProperty<>(FXCollections.observableArrayList());
//    }


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

    public StringProperty passageContentProperty() {
        return passageContent;
    }

    public void setPassageContent(String text) {
        this.passageContent.set(text);
    }

    public String getPassageContent() {
        return passageContent.get();
    }
}

