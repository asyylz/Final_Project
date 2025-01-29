package com.wgapp.worksheetgenerator.Controllers.UI;
import com.wgapp.worksheetgenerator.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    public BorderPane mainWindow;
    public Button generateBtn;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listen for changes in the sub-subject
        Model.getInstance().getSubSubjectProperty().addListener((observable, oldValue, newValue) -> {

        });

        // Listen for changes in the main subject
        Model.getInstance().getMainSubjectProperty().addListener((observable, oldValue, newValue) -> {

        });

        // Listen for clicking generate button
       // generateBtn.setOnAction(event -> onWorksheetGenerateButtonClicked());
    }



    /*================================= LISTENERS ===================================== */
//    private void onWorksheetGenerateButtonClicked() {
//
//        if (Model.getInstance().getQuestionTypeList().isEmpty()) {
//            // Get the modal window view as a parent/root from the ViewFactory
//            VBox modalWindowParent = Model.getInstance().getViewFactory().getModalWindowView();
//
//            // We are getting current window x and y coordinates
//            Stage currentStage = (Stage) mainWindow.getScene().getWindow();
//            System.out.println(currentStage.getTitle());
//            double x = currentStage.getX();
//            double y = currentStage.getY();
//
//            ScaleTransition st = new ScaleTransition(Duration.millis(500), modalWindowParent);
//            st.setInterpolator(Interpolator.EASE_BOTH);
//            st.setFromX(0);
//            st.setFromY(0);
//            st.setToX(1);
//            st.setToY(1);
//
//            // Detach from any existing parent
//            if (modalWindowParent.getScene() != null) {
//                modalWindowParent.getScene().setRoot(new VBox()); // Replace with an empty placeholder
//            }
//
//            //  Set up the modal window stage
//            Stage modalStage = new Stage();
//            modalStage.setTitle("Warning");
//            modalStage.initStyle(StageStyle.TRANSPARENT);
//
//            Scene modalScene = new Scene(modalWindowParent, 400, 200);
//            modalScene.setFill(Color.TRANSPARENT);
//
//            modalStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with other windows
//
//            // Make the modal non-resizable
//            modalStage.setResizable(false);
//            modalStage.setScene(modalScene);
//
//            // Set the stage in the controller for proper handling
//            // Set the stage in the controller for proper handling
//            ModalWindowController controller = (ModalWindowController) modalWindowParent.getProperties().get("controller");
//            if (controller != null) {
//                controller.setStage(modalStage);
//            } else {
//                System.err.println("Controller not found for the modal window!");
//            }
//
//            // Show the modal stage
//            modalStage.showAndWait();
//
//            // Apply the scale transition
//            st.play();
//
//
//        } else {
//           // WorksheetController.generateWorksheet();
//        }
//    }

}






