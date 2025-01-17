package com.wgapp.worksheetgenerator.Models;


import com.wgapp.worksheetgenerator.Views.*;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;

public class Model {
    private static Model model;
    // Fields to store the data for subject, sub-subject, and other settings
// Initialize directly in the field declaration
    private ObjectProperty<MainSubjectOptions> mainSubject = new SimpleObjectProperty<>(MainSubjectOptions.ENGLISH); // Default subject
    private SchoolYearOptions schoolYear = SchoolYearOptions.YEAR1; // Default school year
    private DifficultyLevelOptions difficultyLevel = DifficultyLevelOptions.Grade1; // Default difficulty level
   // private ObjectProperty<ISubSubjectOptions> subSubject;
    // Observable list to hold multiple selected question types
    private ObjectProperty<ISubSubjectOptions> subSubject = new SimpleObjectProperty<>(SubSubjectOptionsEnglish.COMPREHENSION); // Default sub-subject
    private final ListProperty<ComprehensionQuestionTypes> questionTypeList = new SimpleListProperty<>(FXCollections.observableArrayList()); // Empty list for question types

    //private final ViewFactory viewFactory;


    //    private Model() {
//        this.viewFactory = new ViewFactory();
//
//    }

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

//    public ViewFactory getViewFactory() {
//        return viewFactory;
//    }

    /*================================= GETTERS AND SETTER ===================================== */
    public ObjectProperty<MainSubjectOptions> getMainSubject() {
        return mainSubject;
    }

    public void setMainSubject(MainSubjectOptions mainSubject) {
        this.mainSubject.set(mainSubject);
    }

    public SchoolYearOptions getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYearOptions schoolYear) {
        this.schoolYear = schoolYear;
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
}

