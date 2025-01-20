package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.Models.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Models.SubSubjectOptionsEnglish;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class MainWindowController implements Initializable {
    public BorderPane mainWindow;
    public VBox comprehensionOptionsInclude; // Reference to ComprehensionOptions.fxml
    public Button generateBtn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listen for changes in the sub-subject
        Model.getInstance().getSubSubject().addListener((observable, oldValue, newValue) -> {
            updateComprehensionOptionsVisibility();
        });

        // Listen for changes in the main subject
        Model.getInstance().getMainSubject().addListener((observable, oldValue, newValue) -> {
            updateComprehensionOptionsVisibility();
        });

        // Listen for clicking generate button
        generateBtn.setOnAction(event -> onWorksheetGenerateButtonClicked());
    }

    // Update the visibility of the comprehension options based on both the main subject and sub-subject
    private void updateComprehensionOptionsVisibility() {
        // Check the main subject and sub-subject to update visibility
        MainSubjectOptions mainSubject = Model.getInstance().getMainSubject().get();
        ISubSubjectOptions subSubject = Model.getInstance().getSubSubject().get();

        if (mainSubject == MainSubjectOptions.ENGLISH && subSubject instanceof SubSubjectOptionsEnglish) {
            SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) subSubject;

            // Check the selected sub-subject and show/hide question types accordingly
            switch (subSubjectEnglish) {
                case COMPREHENSION:
                    comprehensionOptionsInclude.setVisible(true);
                    break;

                // Add cases for other English sub-subjects
                default:
                    comprehensionOptionsInclude.setVisible(false);
                    break;
            }
        } else {
            // Hide the question types for non-English main subjects or invalid sub-subjects
            comprehensionOptionsInclude.setVisible(false);
        }
    }


    /*================================= LISTENERS ===================================== */
    private void onWorksheetGenerateButtonClicked() {

        if (Model.getInstance().getQuestionTypeList().isEmpty()) {
            // Get the modal window view as a parent/root from the ViewFactory
            VBox modalWindowParent = Model.getInstance().getViewFactory().getModalWindowView();

            // We are gettin current window x and y coordinates
            Stage currentStage = (Stage) mainWindow.getScene().getWindow();
            System.out.println(currentStage.getTitle());
            double x = currentStage.getX();
            double y = currentStage.getY();

            ScaleTransition st = new ScaleTransition(Duration.millis(500), modalWindowParent);
            st.setInterpolator(Interpolator.EASE_BOTH);
            st.setFromX(0);
            st.setFromY(0);
            st.setToX(1);
            st.setToY(1);

            // Detach from any existing parent
            if (modalWindowParent.getScene() != null) {
                modalWindowParent.getScene().setRoot(new VBox()); // Replace with an empty placeholder
            }

            //  Set up the modal window stage
            Stage modalStage = new Stage();
            modalStage.setTitle("Warning");
            modalStage.initStyle(StageStyle.TRANSPARENT);

            Scene modalScene = new Scene(modalWindowParent, 400, 200);
            modalScene.setFill(Color.TRANSPARENT);

            modalStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with other windows

            // Make the modal non-resizable
            modalStage.setResizable(false);
            modalStage.setScene(modalScene);

            // Set the stage in the controller for proper handling
            // Set the stage in the controller for proper handling
            ModalWindowController controller = (ModalWindowController) modalWindowParent.getProperties().get("controller");
            if (controller != null) {
                controller.setStage(modalStage);
            } else {
                System.err.println("Controller not found for the modal window!");
            }

            // Show the modal stage
            modalStage.showAndWait();

            // Apply the scale transition
            st.play();


        } else {
            WorksheetController.generateWorksheet();
        }
    }

}






