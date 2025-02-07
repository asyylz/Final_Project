package com.wgapp.worksheetgenerator;

import com.wgapp.worksheetgenerator.Config.DatabaseConnection;
import com.wgapp.worksheetgenerator.ViewFactory.ViewFactory;

import javafx.application.Application;
import javafx.stage.Stage;


public class App extends Application {
    @Override
    public void start(Stage stage) {
        // Get the ViewFactory from the Model
        ViewFactory viewFactory = new ViewFactory();

   //  viewFactory.showMainWindow();
//  viewFactory.showLandingWindow();
      //  viewFactory.showMainWindow();
//       viewFactory.showGeneratorWindow();

  viewFactory.showLoginWindow();


        DatabaseConnection.getConnection();
    }
}
