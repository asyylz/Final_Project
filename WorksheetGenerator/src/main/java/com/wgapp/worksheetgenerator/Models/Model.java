package com.wgapp.worksheetgenerator.Models;


import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.Views.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class Model {
    private static Model model;
    //private Worksheet worksheet;
    private UserDTO user;
    private final ViewFactory viewFactory;

    // Worksheet
    private final ObjectProperty<Worksheet> worksheet = new SimpleObjectProperty<>();

    // Initialize directly in the field declaration
    private ObjectProperty<MainSubjectOptions> mainSubject = new SimpleObjectProperty<>();
    private ObjectProperty<DifficultyLevelOptions> difficultyLevel = new SimpleObjectProperty<>();
    private ObjectProperty<ISubSubjectOptions> subSubject = new SimpleObjectProperty<>();

    // Comprehension
    private final ListProperty<ComprehensionQuestionTypes> questionTypeList = new SimpleListProperty<>(FXCollections.observableArrayList()); // Empty list for question types

    // Passage
    private StringProperty passageContent = new SimpleStringProperty("");
    private StringProperty passageTitle = new SimpleStringProperty("");

    //User Answer List
    private ListProperty<UserAnswer> userAnswerList = new SimpleListProperty<>(FXCollections.observableArrayList());

    // Search box
    StringProperty searchTerm = new SimpleStringProperty("");

    // User
    StringProperty userName = new SimpleStringProperty("");
    StringProperty password = new SimpleStringProperty("");
    StringProperty pin = new SimpleStringProperty("");


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
    public ObjectProperty<MainSubjectOptions> getMainSubjectProperty() {
        return mainSubject;
    }

    public void setMainSubject(MainSubjectOptions mainSubject) {
        this.mainSubject.set(mainSubject);
    }

    public DifficultyLevelOptions getDifficultyLevel() {
        return difficultyLevel.get();
    }

    public ObjectProperty<DifficultyLevelOptions> getDifficultyLevelProperty() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(DifficultyLevelOptions difficultyLevel) {
        this.difficultyLevel.set(difficultyLevel);
    }

    public ObjectProperty<ISubSubjectOptions> getSubSubjectProperty() {
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

    public void addQuestionType(ComprehensionQuestionTypes questionType) {
        this.questionTypeList.add(questionType);
    }

    public void removeQuestionsFromList() {
        this.questionTypeList.clear();
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

    public StringProperty getPassageContentProperty() {
        return passageContent;
    }

    public String getPassageTitle() {
        return passageTitle.get();
    }

    public StringProperty getPassageTitleProperty() {
        return passageTitle;
    }

    public ListProperty getQuestionTypeListProperty() {
        return questionTypeList;
    }

    public void setPassageTitle(String passageTitle) {
        this.passageTitle.set(passageTitle);
    }

//    public Worksheet getWorksheet() {
//        return worksheet;
//    }
//
//    public void setWorksheet(Worksheet worksheet) {
//        this.worksheet = worksheet;
//    }

    public ListProperty<UserAnswer> getUserAnswersListProperty() {
        return userAnswerList;
    }

    public List<UserAnswer> getUserAnswersList() {
        return userAnswerList.get();
    }

    public void addUserAnswerToUserAnswerList(UserAnswer choice) {
        userAnswerList.add(choice);
    }

    public String getUserName() {
        return userName.get();
    }

    public StringProperty userNameProperty() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getPin() {
        return pin.get();
    }

    public StringProperty pinProperty() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin.set(pin);
    }

    public String getSearchTerm() {
        return searchTerm.get();
    }

    public StringProperty searchTermProperty() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm.set(searchTerm);
    }

    public Worksheet getWorksheet() {
        return worksheet.get();
    }

    public ObjectProperty<Worksheet> worksheetProperty() {
        return worksheet;
    }
    public void setWorksheet(Worksheet worksheet) {
        this.worksheet.set(worksheet);
    }
}

