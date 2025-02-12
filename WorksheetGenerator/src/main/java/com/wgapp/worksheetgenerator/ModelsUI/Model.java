package com.wgapp.worksheetgenerator.ModelsUI;

import com.wgapp.worksheetgenerator.ViewFactory.*;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Model  {
    private static Model model;
    private final ViewFactory viewFactory;

    //This object will hold data from fields to update UI (Coming)
    private ObjectProperty<WorksheetProperty> worksheetProperty = new SimpleObjectProperty<>(new WorksheetProperty());
    // This object will hold data from fields to generate worksheet with different constructor (Going)
    private ObjectProperty<WorksheetProperty> worksheetPropertyForGeneration = new SimpleObjectProperty<>(new WorksheetProperty());

    // Holds user's data for UI
    private ObjectProperty<UserProperty> userProperty = new SimpleObjectProperty<>(new UserProperty());

    // List Worksheet
    private ListProperty<WorksheetProperty> worksheetPropertyList = new SimpleListProperty<>(FXCollections.observableArrayList());

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

    /*================================= WORKSHEET LIST ===================================== */

    public ObservableList<WorksheetProperty> getWorksheetPropertyList() {
        return worksheetPropertyList.get();
    }


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


    public WorksheetProperty getWorksheetPropertyForGeneration() {
        return worksheetPropertyForGeneration.get();
    }

    //============================================== WORKSHEET AND OBSERVER =================================================//
    public WorksheetProperty getWorksheetProperty() {
        return worksheetProperty.get();
    }

    public ObjectProperty<WorksheetProperty> worksheetProperty() {
        return worksheetProperty;
    }

    public void setWorksheetProperty(WorksheetProperty worksheetProperty) {

        this.worksheetProperty.set(worksheetProperty);
    }

}


