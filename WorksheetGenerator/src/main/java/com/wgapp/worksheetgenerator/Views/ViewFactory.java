package com.wgapp.worksheetgenerator.Views;

import com.wgapp.worksheetgenerator.Controllers.UI.MainWindowController;
import com.wgapp.worksheetgenerator.Controllers.UI.ModalWindowController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ViewFactory {
    // private MainSubjectOptions mainSubject;
    // private Object subSubject; // Used Object to allow dynamic types (can hold any enum type)
    //  private SchoolYearOptions schoolYear;
    // private DifficultyLevelOptions difficultyLevel;

    // Observable subSubject property
    //  private ObjectProperty<ISubSubjectOptions> subSubject;

//    private Model model; // ViewFactory gets the data from Model
//
//    public ViewFactory() {
//        model = Model.getInstance(); // Get instance of the Model
//    }

    // Partial views
    private HBox questionTypesView;
    private VBox modalWindowView;


//    public ViewFactory() {
//        this.mainSubject = MainSubjectOptions.ENGLISH; // English is set by default
//        this.subSubject = new SimpleObjectProperty<>(SubSubjectOptionsEnglish.COMPREHENSION); // Comprehension is set by default
//        this.schoolYear = SchoolYearOptions.YEAR1; // Year1 is set by default
//        this.difficultyLevel = DifficultyLevelOptions.Grade1; // Grade1 is set by default
//    }


    /*================================= VIEW METHODS ===================================== */
    public VBox getModalWindowView() {
        if (modalWindowView == null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ModalWindow.fxml"));
                modalWindowView = loader.load();

                // Store the controller as a property of the VBox
                ModalWindowController controller = loader.getController();
                modalWindowView.getProperties().put("controller", controller);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return modalWindowView;
    }

    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/MainWindow.fxml"));
        MainWindowController mainWindowController = new MainWindowController();
        loader.setController(mainWindowController);
        createStage(loader, 1100, 1100);
    }

    public void showLandingWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LandingWindow.fxml"));
        createStage(loader, 500, 1010);

    }

    public void showGeneratorWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/GeneratorWindow.fxml"));

        String stylesheetPath = getClass().getResource("/Styles/CustomDropdownStyle.css").toExternalForm();
        createStage(loader, stylesheetPath, 630, 900);
    }

    public void showPassageWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PassageWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/PassageWindow.css").toExternalForm();
        createStage(loader, stylesheetPath, 900, 700);
    }

    /*================================= STAGE METHODS ===================================== */
    private void createStage(FXMLLoader loader, String stylesheetPath, int width, int height) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            scene.getStylesheets().add(stylesheetPath);  // Add stylesheet to the scene here
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Worksheet Generator");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.show();
    }

    private void createStage(FXMLLoader loader, int width, int height) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Worksheet Generator");
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.show();
    }

    public void closeStage(Stage stage) {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.millis(200)); // Set duration (500ms for smooth fade)
        fadeOut.setNode(stage.getScene().getRoot()); // Apply the transition to the root node
        fadeOut.setFromValue(1.0); // Start fully visible
        fadeOut.setToValue(0); // End fully invisible
        // Set an event after the transition finishes to close the stage
        fadeOut.setOnFinished(event -> stage.close());
        // Play the transition
        fadeOut.play();

        //stage.close();
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
