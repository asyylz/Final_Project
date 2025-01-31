package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    public BorderPane mainWindow;

    //main window controller
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Model.getInstance().getViewFactory().getUserSelectMenuView().addListener((observable, oldValue, newValue) -> {
            mainWindow.setCenter(Model.getInstance().getViewFactory().getView(newValue));
        });


    }
}





