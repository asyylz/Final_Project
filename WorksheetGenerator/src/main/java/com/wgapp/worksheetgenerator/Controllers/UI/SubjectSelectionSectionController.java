package com.wgapp.worksheetgenerator.Controllers.UI;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Views.*;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import java.net.URL;
import java.util.ResourceBundle;

public class SubjectSelectionSectionController implements Initializable {

    public ChoiceBox<MainSubjectOptions> mainSubject;
    public ChoiceBox<ISubSubjectOptions> subSubject;
    public ChoiceBox<SchoolYearOptions> schoolYear;
    public ChoiceBox<DifficultyLevelOptions> difficultyLevel;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Populating mainSubject ChoiceBox
        mainSubject.setItems(FXCollections.observableArrayList(MainSubjectOptions.values()));
        // Setting English mainSubject by default
       // mainSubject.setValue(Model.getInstance().getViewFactory().getMainSubject());
        //new
        mainSubject.setValue(Model.getInstance().getMainSubject().getValue());

        // Populating subSubject ChoiceBox
        subSubject.setItems(FXCollections.observableArrayList(SubSubjectOptionsEnglish.values()));
        // Setting Comprehension initial SubSubject (default to English)
        //subSubject.setValue(Model.getInstance().getViewFactory().getSubSubject().getValue());
        //new
        subSubject.setValue(Model.getInstance().getSubSubject().getValue());

        // Populating schoolYear ChoiceBox
        schoolYear.setItems(FXCollections.observableArrayList(SchoolYearOptions.values()));
        //schoolYear.setValue(Model.getInstance().getViewFactory().getSchoolYear());
        //new
        schoolYear.setValue(Model.getInstance().getSchoolYear());

        // Populating difficultyLevel ChoiceBox
        difficultyLevel.setItems(FXCollections.observableArrayList(DifficultyLevelOptions.values()));
        //difficultyLevel.setValue(Model.getInstance().getViewFactory().getDifficultyLevel());
        //new
        difficultyLevel.setValue(Model.getInstance().getDifficultyLevel());

        onMainSubjectSelectionListener();
        onSubSubjectSelectionListener();
        onSchoolYearSelectionListener();
        onDifficultyLevelSelectionListener();


    }

    /*================================= LISTENERS ===================================== */
    private void onMainSubjectSelectionListener() {
        // Add listener to mainSubject choice box
        mainSubject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == MainSubjectOptions.ENGLISH) {
                // Populate subSubject with English-specific options
                subSubject.setItems(FXCollections.observableArrayList(SubSubjectOptionsEnglish.values()));
                subSubject.setValue(SubSubjectOptionsEnglish.COMPREHENSION);
                //Update mainSubject in ViewFactory
                //Model.getInstance().getViewFactory().setMainSubject(MainSubjectOptions.ENGLISH);
                //new
                Model.getInstance().setMainSubject(MainSubjectOptions.ENGLISH);
            } else if (newValue == MainSubjectOptions.MATHS) {
                // Populate subSubject with Math-specific options
                subSubject.setItems(FXCollections.observableArrayList(SubSubjectOptionsMaths.values()));
                subSubject.setValue(SubSubjectOptionsMaths.FRACTIONS);
                //Update mainSubject in ViewFactory
                //Model.getInstance().getViewFactory().setMainSubject(MainSubjectOptions.MATHS);
                //new
                Model.getInstance().setMainSubject(MainSubjectOptions.MATHS);
            } else {
                // Clear subSubject options for other selections
                subSubject.setItems(FXCollections.observableArrayList());
            }
        });
    }

    private void onSubSubjectSelectionListener() {
        subSubject.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            subSubject.setValue(newValue);
            // Update subSubject in ViewFactory
            // Calling .set(newValue) directly updates the value and automatically notifies any listeners that are observing this property.
           // Model.getInstance().getViewFactory().getSubSubject().set(newValue);
            //new
            Model.getInstance().getSubSubject().set(newValue);
            System.out.println("Sub-Subject: line 89 " + Model.getInstance().getSubSubject());
        });
    }

    private void onSchoolYearSelectionListener() {
        schoolYear.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            schoolYear.setValue(newValue);
            // Update subSubject in ViewFactory
          //  Model.getInstance().getViewFactory().setSchoolYear(schoolYear.getValue());
            //new
            Model.getInstance().setSchoolYear(schoolYear.getValue());
            System.out.println("School Year: " + Model.getInstance().getSchoolYear());
        });
    }

    private void onDifficultyLevelSelectionListener() {
        difficultyLevel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            difficultyLevel.setValue(newValue);
            // Update subSubject in ViewFactory
           // Model.getInstance().getViewFactory().setDifficultyLevel(difficultyLevel.getValue());
            //new
            Model.getInstance().setDifficultyLevel(difficultyLevel.getValue());
           // System.out.println("Difficulty Level: " + Model.getInstance().getDifficultyLevel());
        });
    }





    // Later I will use this for onMainSubjectSelectionListener
//    private void updateSubSubjectItems(Object[] items, Object defaultValue) {
//        subSubject.setItems(FXCollections.observableArrayList(items));
//        subSubject.setValue(defaultValue);
//        Model.getInstance().getViewFactory().setSubSubject(defaultValue);
//        //updateSubSubjectItems(SubSubjectOptionsMaths.values(), SubSubjectOptionsMaths.FRACTIONS);
//        //updateSubSubjectItems(SubSubjectOptionsEnglish.values(), SubSubjectOptionsEnglish.COMPREHENSION);
//
//    /*================================= === ===================================== */



}
