package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;


public class MainWindowController implements Initializable {
    public BorderPane mainWindow;

    //main window controller
    public void initialize(URL url, ResourceBundle resourceBundle) {

     //   AnchorPane node = (AnchorPane) mainWindow.getChildren().get(1);
        // Bind the width of node to the width of mainWindow minus 200
     //   node.minWidthProperty().bind(mainWindow.widthProperty().subtract(200));

        //System.out.println(node);


        Model.getInstance().getViewFactory().getUserSelectMenuView().addListener((observable, oldValue, newValue) -> {
            mainWindow.setCenter(Model.getInstance().getViewFactory().getView(newValue));
        });


    }
}





