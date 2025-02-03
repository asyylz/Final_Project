package com.wgapp.worksheetgenerator.ModelsUI;

import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.ViewFactory.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import java.util.List;

public class Model {
    private static Model model;
    private UserDTO user;
    private final ViewFactory viewFactory;

    //This object will hold data from fields to update UI (Coming)
    private ObjectProperty<WorksheetProperty> worksheetProperty = new SimpleObjectProperty<>(new WorksheetProperty());
    // This object will hold data from fields to generate worksheet with different constructor (Going)
    private ObjectProperty<WorksheetProperty> worksheetPropertyForGeneration = new SimpleObjectProperty<>(new WorksheetProperty());


    //User Answer List
    //private ListProperty<UserAnswer> userAnswerList = new SimpleListProperty<>(FXCollections.observableArrayList());

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

    /*================================= GETTERS AND SETTER ===================================== */

//    public ListProperty<UserAnswer> getUserAnswersListProperty() {
//        return userAnswerList;
//    }
//
//    public List<UserAnswer> getUserAnswersList() {
//        return userAnswerList.get();
//    }
//
//    public void addUserAnswerToUserAnswerList(UserAnswer choice) {
//        userAnswerList.add(choice);
//    }

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

    public WorksheetProperty getWorksheetProperty() {
        return worksheetProperty.get();
    }

    public ObjectProperty<WorksheetProperty> worksheetProperty() {
        return worksheetProperty;
    }

    public void setWorksheetProperty(WorksheetProperty worksheetProperty) {
        this.worksheetProperty.set(worksheetProperty);
    }

    public WorksheetProperty getWorksheetPropertyForGeneration() {
        return worksheetPropertyForGeneration.get();
    }

    public ObjectProperty<WorksheetProperty> worksheetPropertyForGenerationProperty() {
        return worksheetPropertyForGeneration;
    }

    public void setWorksheetPropertyForGeneration(WorksheetProperty worksheetPropertyForGeneration) {
        this.worksheetPropertyForGeneration.set(worksheetPropertyForGeneration);
    }
}


