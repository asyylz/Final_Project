package com.wgapp.worksheetgenerator.Controllers.UI;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalWindowController implements Initializable {
    public Label messageLabel;
    public Button okBtn;
    public Stage stage;
    public VBox modalWindow;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println(stage.getTitle()); // return null
            // Check if the current stage is the modal window stage
            if (stage != null && stage.isShowing()) {
                System.out.println("OK button clicked");
                stage.close();  // Close the modal window
            }
        });
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
