package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Model;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LandingWindowController implements Initializable {
    public AnchorPane landingWindow;
    public Button letsStartBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        letsStartBtn.setFocusTraversable(Boolean.FALSE);

        Platform.runLater(() -> {
            Stage stage = (Stage) letsStartBtn.getScene().getWindow();
            // In your start method or where you create your stage
            stage.setOnCloseRequest(event -> {
                //event.consume(); // This prevents the window from closing
                Model.getInstance().getViewFactory().showMainWindow();

            });

            letsStartBtn.setOnAction(e -> {
                Model.getInstance().getViewFactory().closeStage(stage);
                Model.getInstance().getViewFactory().showMainWindow();
            });
        });

    }
}
