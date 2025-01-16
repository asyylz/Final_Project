package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.Views.SubSubjectOptionsEnglish;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public BorderPane mainWindow;
    public VBox questionTypesInclude; // Reference to QuestionTypes.fxml

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(questionTypesInclude.getChildren());

        Model.getInstance().getViewFactory().getSubSubject().addListener((observable, oldValue, newValue) -> {
            // Check if the newValue is an instance of SubSubjectOptions
            if (newValue instanceof ISubSubjectOptions) {
                // Access the specific type based on the instance
                if (newValue instanceof SubSubjectOptionsEnglish) {
                    SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) newValue;
                    switch (subSubjectEnglish) {
                        case COMPREHENSION -> questionTypesInclude.setVisible(true);
                        // mainWindow.setTop(Model.getInstance().getViewFactory().getQuestionTypesView());

                        default -> questionTypesInclude.setVisible(false);
                        // Add cases for other English sub-subjects

                    }
                }

            }
        });
    }
}




