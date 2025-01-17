package com.wgapp.worksheetgenerator.Views;

import com.wgapp.worksheetgenerator.Controllers.MainWindowController;
import com.wgapp.worksheetgenerator.Models.Model;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {
   // private MainSubjectOptions mainSubject;
    // private Object subSubject; // Used Object to allow dynamic types (can hold any enum type)
  //  private SchoolYearOptions schoolYear;
   // private DifficultyLevelOptions difficultyLevel;

    // Observable subSubject property
  //  private ObjectProperty<ISubSubjectOptions> subSubject;

    private Model model; // ViewFactory gets the data from Model

    public ViewFactory() {
        model = Model.getInstance(); // Get instance of the Model
    }

    // Partial views
    private HBox questionTypesView;
    private HBox emptyView;


//    public ViewFactory() {
//        this.mainSubject = MainSubjectOptions.ENGLISH; // English is set by default
//        this.subSubject = new SimpleObjectProperty<>(SubSubjectOptionsEnglish.COMPREHENSION); // Comprehension is set by default
//        this.schoolYear = SchoolYearOptions.YEAR1; // Year1 is set by default
//        this.difficultyLevel = DifficultyLevelOptions.Grade1; // Grade1 is set by default
//    }


    /*================================= VIEW METHODS ===================================== */

    public HBox getQuestionTypesView() {
        if (questionTypesView == null) {
            try {
                questionTypesView = new FXMLLoader(getClass().getResource("/Fxml/ComprehensionOptions.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return questionTypesView;
    }

    public HBox getEmptyView() {
        if (emptyView == null) {
            try {
                emptyView = new FXMLLoader(getClass().getResource("/Fxml/EmptyHBox.fxml")).load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return emptyView;
    }

    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MainWindow.fxml"));
        MainWindowController mainWindowController = new MainWindowController();
        loader.setController(mainWindowController);
        createStage(loader);
    }

    /*================================= STAGE METHODS ===================================== */
    private void createStage(FXMLLoader loader) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Worksheet Generator");
        stage.setMinWidth(1100);
        stage.setMinHeight(1000);
        stage.show();
    }

    public void closeStage(Stage stage) {
        stage.close();
    }

    /*================================= GETTERS AND SETTERS ===================================== */

//    public MainSubjectOptions getMainSubject() {
//        return mainSubject;
//    }
//
//    public void setMainSubject(MainSubjectOptions mainSubject) {
//        this.mainSubject = mainSubject;
//    }
//
//    // Getter for subSubject (returns the ObjectProperty)
//    public ObjectProperty<ISubSubjectOptions> getSubSubject() {
//        return subSubject;
//    }
//
//    public void setSubSubject(ObjectProperty<ISubSubjectOptions> subSubject) {
//        this.subSubject = subSubject;
//    }
//
//    public SchoolYearOptions getSchoolYear() {
//        return schoolYear;
//    }
//
//    public void setSchoolYear(SchoolYearOptions schoolYear) {
//        this.schoolYear = schoolYear;
//    }
//
//    public DifficultyLevelOptions getDifficultyLevel() {
//        return difficultyLevel;
//    }
//
//    public void setDifficultyLevel(DifficultyLevelOptions difficultyLevel) {
//        this.difficultyLevel = difficultyLevel;
//    }
}
