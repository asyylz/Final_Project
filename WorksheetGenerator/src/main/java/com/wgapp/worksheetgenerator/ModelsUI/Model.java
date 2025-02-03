package com.wgapp.worksheetgenerator.ModelsUI;

import com.wgapp.worksheetgenerator.DTOs.UserDTO;
import com.wgapp.worksheetgenerator.ViewFactory.*;
import javafx.beans.property.*;

public class Model {
    private static Model model;
    private UserDTO user;
    private final ViewFactory viewFactory;

    //This object will hold data from fields to update UI (Coming)
    private ObjectProperty<WorksheetProperty> worksheetProperty = new SimpleObjectProperty<>(new WorksheetProperty());
    // This object will hold data from fields to generate worksheet with different constructor (Going)
    private ObjectProperty<WorksheetProperty> worksheetPropertyForGeneration = new SimpleObjectProperty<>(new WorksheetProperty());

    // Holds user'd data for UI
    private ObjectProperty<UserProperty> userProperty = new SimpleObjectProperty<>(new UserProperty());

    // Search box
    StringProperty searchTerm = new SimpleStringProperty("");

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

//============================================== USER =================================================//
    public UserProperty getUserProperty() {
        return userProperty.get();
    }

    public ObjectProperty<UserProperty> userProperty() {
        return userProperty;
    }

    public void setUserProperty(UserProperty userProperty) {
        this.userProperty.set(userProperty);
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


