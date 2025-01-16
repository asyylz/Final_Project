package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.Views.SubSubjectOptionsEnglish;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public BorderPane mainWindow;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model.getInstance().getViewFactory().getSubSubject().addListener((observable, oldValue, newValue) -> {
            // Check if the newValue is an instance of SubSubjectOptions
            if (newValue instanceof ISubSubjectOptions) {
                // Access the specific type based on the instance
                if (newValue instanceof SubSubjectOptionsEnglish) {
                    SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) newValue;
                    switch (subSubjectEnglish) {
                        case COMPREHENSION ->
                                mainWindow.setCenter(Model.getInstance().getViewFactory().getQuestionTypesView());
                        case CLOZETEST -> mainWindow.setCenter(Model.getInstance().getViewFactory().getEmptyView());
                        default -> mainWindow.setCenter(null);
                        // Add cases for other English sub-subjects

                    }
                }

            }
        });
    }
}




