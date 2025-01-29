package com.wgapp.worksheetgenerator.Views;

import com.wgapp.worksheetgenerator.Controllers.UI.ModalWindowController;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;


public class ViewFactory {

    /*================================= VIEW METHODS ===================================== */
    public void showModalWindow(String warningText) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/ModalWindow.fxml"));

        try {
            // Load the FXML and get the controller
            Scene scene = new Scene(loader.load());
            ModalWindowController controller = loader.getController();

            // Pass the warning text to the controller
            controller.setMessageText(warningText);

            // Create and show the modal window
            createStageForModalWindow(scene, 400, 200, "Warning");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showLandingWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/LandingWindow.fxml"));
        createStage(loader, 500, 1010, "Welcome Worksheet Generator Application");

    }

    public void showGeneratorWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/GeneratorWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/CustomDropdownStyle.css").toExternalForm();
        createStage(loader, stylesheetPath, 630, 900, "Worksheet Generator");
    }

    public void showPassageWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/PassageWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/PassageWindow.css").toExternalForm();
        createStage(loader, stylesheetPath, 900, 700, "Reading Passage");
    }

    public void showWorksheetWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/WorksheetWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/QuestionComponent.css").toExternalForm();
        createStage(loader, stylesheetPath, 800, 1000, "Worksheet");
    }

    public void showWorksheetWindowWithPassage() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/WorksheetWindowWithPassage.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/WorksheetWindowWithPassage.css").toExternalForm();
        createStage(loader, stylesheetPath, 1100, 1000, "English Worksheet");
    }

    /*================================= STAGE METHODS ===================================== */
    private void createStage(FXMLLoader loader, String stylesheetPath, int width, int height, String title) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            scene.getStylesheets().add(stylesheetPath);  // Add stylesheet to the scene here
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        //stage.setResizable(false);
        stage.show();
    }

    private void createStage(FXMLLoader loader, int width, int height, String stageTitle) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();

        // This prevents the window from closing via x button
        stage.setOnCloseRequest(event -> {
           // event.consume();
            //showGeneratorWindow();
        });

        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.show();
    }

    private void createStageForModalWindow(Scene scene, int width, int height, String stageTitle) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);  // Blocks interaction with other windows
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        stage.showAndWait();

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
        stage.close();
    }

}
