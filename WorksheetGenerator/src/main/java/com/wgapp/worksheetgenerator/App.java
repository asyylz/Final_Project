package com.wgapp.worksheetgenerator;

import com.wgapp.worksheetgenerator.Database.DatabaseConnection;
import com.wgapp.worksheetgenerator.Views.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
//    public void start(Stage stage) {
//        // Model.getInstance().getViewFactory().showGeneratorWindow();
//       Model.getInstance().getViewFactory().showMainWindow();
//
//    }
    public void start(Stage stage) {
        // Get the Model's instance
//       Model model = Model.getInstance();
//        System.out.println(model.getSubSubject());

        // Get the ViewFactory from the Model
        ViewFactory viewFactory = new ViewFactory();

        // Show the main window using the ViewFactory
        //viewFactory.showMainWindow();
         //viewFactory.showLandingWindow();
        viewFactory.showGeneratorWindow();


        DatabaseConnection.getConnection();
    }
}
