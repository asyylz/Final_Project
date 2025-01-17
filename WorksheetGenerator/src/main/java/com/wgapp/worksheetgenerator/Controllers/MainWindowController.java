package com.wgapp.worksheetgenerator.Controllers;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.Views.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Views.SubSubjectOptionsEnglish;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    public BorderPane mainWindow;
    public VBox questionTypesInclude; // Reference to ComprehensionOptions.fxml

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Listen for changes in the sub-subject
        Model.getInstance().getSubSubject().addListener((observable, oldValue, newValue) -> {
            updateComprehensionOptionsVisibility();
        });

        // Listen for changes in the main subject
        Model.getInstance().getMainSubject().addListener((observable, oldValue, newValue) -> {
            updateComprehensionOptionsVisibility();
        });
    }

    // Update the visibility of the question types based on both the main subject and sub-subject
    private void updateComprehensionOptionsVisibility() {
        // Check the main subject and sub-subject to update visibility
        MainSubjectOptions mainSubject = Model.getInstance().getMainSubject().get();
        ISubSubjectOptions subSubject = Model.getInstance().getSubSubject().get();

        if (mainSubject == MainSubjectOptions.ENGLISH && subSubject instanceof SubSubjectOptionsEnglish) {
            SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) subSubject;

            // Check the selected sub-subject and show/hide question types accordingly
            switch (subSubjectEnglish) {
                case COMPREHENSION:
                    questionTypesInclude.setVisible(true);
                    break;

                // Add cases for other English sub-subjects
                default:
                    questionTypesInclude.setVisible(false);
                    break;
            }
        } else {
            // Hide the question types for non-English main subjects or invalid sub-subjects
            questionTypesInclude.setVisible(false);
        }
    }


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Model.getInstance().getSubSubject().addListener((observable, oldValue, newValue) -> {
//            // Check if the newValue is an instance of SubSubjectOptions
//            if (newValue instanceof ISubSubjectOptions) {
//                // Access the specific type based on the instance
//                if (newValue instanceof SubSubjectOptionsEnglish) {
//                    SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) newValue;
//                    switch (subSubjectEnglish) {
//                        case COMPREHENSION -> questionTypesInclude.setVisible(true);
//                        // mainWindow.setTop(Model.getInstance().getViewFactory().getQuestionTypesView());
//
//                        default -> questionTypesInclude.setVisible(false);
//                        // Add cases for other English sub-subjects
//                    }
//                }
//            }
//        });
//    }


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Model.getInstance().getViewFactory().getSubSubject().addListener((observable, oldValue, newValue) -> {
//            // Check if the newValue is an instance of SubSubjectOptions
//            if (newValue instanceof ISubSubjectOptions) {
//                // Access the specific type based on the instance
//                if (newValue instanceof SubSubjectOptionsEnglish) {
//                    SubSubjectOptionsEnglish subSubjectEnglish = (SubSubjectOptionsEnglish) newValue;
//                    switch (subSubjectEnglish) {
//                        case COMPREHENSION -> questionTypesInclude.setVisible(true);
//                        // mainWindow.setTop(Model.getInstance().getViewFactory().getQuestionTypesView());
//
//                        default -> questionTypesInclude.setVisible(false);
//                        // Add cases for other English sub-subjects
//                    }
//                }
//            }
//        });
//    }
}




