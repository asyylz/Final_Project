package com.wgapp.worksheetgenerator;

import com.wgapp.worksheetgenerator.Models.Model;
import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        // Model.getInstance().getViewFactory().showGeneratorWindow();
        Model.getInstance().getViewFactory().showMainWindow();
    }
}
