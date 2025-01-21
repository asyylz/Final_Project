package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.Model;
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

        //OnClick letStartBtn , landing window will be closed and generatingWindow will appear
        letsStartBtn.setOnAction(e -> {
            Stage stage = (Stage) letsStartBtn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(stage);
            Model.getInstance().getViewFactory().showGeneratorWindow();
        });
    }
}
