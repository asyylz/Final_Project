package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.Database.WorksheetDAOImpl;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Services.OpenAIService;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;


import java.net.URL;
import java.util.ResourceBundle;

public class GeneratorWindowController implements Initializable {
    public AnchorPane generatorWindowParent;
    public Pane wrapperCustomDropdownMenuOne;
    public Pane wrapperCustomDropdownMenuTwo;
    public Pane wrapperCustomDropdownMenuThree;
    public Button generateBtn;
    public HBox passageTextWrapper;
    public Button passageBtn;
    public Button clearSelectionBtn;
    public StackPane loadingIndicatorComponent;
    private BooleanProperty allDropdownsSelected = new SimpleBooleanProperty(false);

    private WorksheetController worksheetController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WorksheetService worksheetService = new WorksheetService(new OpenAIService(), new WorksheetDAOImpl());
        this.worksheetController = new WorksheetController(worksheetService);

        // Default we are setting indicator's visibility false
        loadingIndicatorComponent.setVisible(false);

        //Set font family Oswald
        Font.loadFont(GeneratorWindowController.class.getResourceAsStream("/Fonts/Oswald/Oswald-VariableFont_wght.ttf"), 12);
        generatorWindowParent.setStyle("-fx-font-family: 'Oswald'; -fx-font-size: 14px;");

        // Initially disable the generate button
        generateBtn.setDisable(true);

        // Initialize CustomDropdowns and populating their content
        CustomDropdownMenu dropdownMainSubject = new CustomDropdownMenu("MAIN SUBJECT", MainSubjectOptions.values());
        CustomDropdownMenu dropdownSubSubject = new CustomDropdownMenu("SUB SUBJECT");
        CustomDropdownMenu difficultyLevel = new CustomDropdownMenu("DIFFICULTY LEVEL", DifficultyLevelOptions.values());

        // Add CustomDropdownMenus to the associated panes in layout
        wrapperCustomDropdownMenuOne.getChildren().add(dropdownMainSubject);
        wrapperCustomDropdownMenuTwo.getChildren().add(dropdownSubSubject);
        wrapperCustomDropdownMenuThree.getChildren().add(difficultyLevel);
        passageTextWrapper.setVisible(false);


        //LISTENER
        // Set up model the connection mainSubjectDropdown
        dropdownMainSubject.setOnSelectionChanged(event -> {
            String selectedMain = dropdownMainSubject.getSelectedValue();
            switch (selectedMain) {
                case "ENGLISH":
                    dropdownSubSubject.setDropdownContent(SubSubjectOptionsEnglish.values());
                    dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
                    Model.getInstance().setMainSubject(MainSubjectOptions.ENGLISH);
                    break;
                case "MATHS":
                    dropdownSubSubject.setDropdownContent(SubSubjectOptionsMaths.values());
                    dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
                    Model.getInstance().setMainSubject(MainSubjectOptions.MATHS);
                    break;
            }
            checkAllDropdownsSelected();
        });

        //LISTENER
        // Set up model the connection subSubjectDropdown
        dropdownSubSubject.setOnSelectionChanged(subSubjectEvent -> onSubSubjectDropdownHandler(dropdownSubSubject));

        //LISTENER
        // Set up model the connection difficultyLevel
        difficultyLevel.setOnSelectionChanged(difficultyLevelEvent -> {
            String selectedDifficulty = difficultyLevel.getSelectedValue();
            DifficultyLevelOptions difficultyLevelOptions = DifficultyLevelOptions.valueOf(selectedDifficulty);
            Model.getInstance().setDifficultyLevel(difficultyLevelOptions);
            checkAllDropdownsSelected();
        });

        //LISTENER
        // generateBtn listens for click event to generate worksheet
        generateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            onWorksheetGenerateButtonClickedHandler();
        });


        // Bind generate button's disable property to the inverse of allDropdownsSelected
        generateBtn.disableProperty().bind(allDropdownsSelected.not());

        // Handle clicks outside the dropdowns
        generatorWindowParent.setOnMouseClicked(event -> {
            if (!dropdownMainSubject.getBoundsInParent().contains(event.getX(), event.getY())) {
                dropdownMainSubject.closeDropdown();
            }
            if (!dropdownSubSubject.getBoundsInParent().contains(event.getX(), event.getY())) {
                dropdownSubSubject.closeDropdown();
            }
            if (!difficultyLevel.getBoundsInParent().contains(event.getX(), event.getY())) {
                difficultyLevel.closeDropdown();
            }
        });

        clearSelectionBtn.setOnMouseClicked(event -> {
            dropdownMainSubject.setMainButtonText("MAIN SUBJECT" + " ▼");
            dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
            difficultyLevel.setMainButtonText("DIFFICULTY LEVEL" + " ▼");
            allDropdownsSelected.set(false);
            Model.getInstance().setMainSubject(null);
            Model.getInstance().setDifficultyLevel(null);
            Model.getInstance().setSubSubject(null);

        });

    }

    private void checkAllDropdownsSelected() {
        System.out.println(Model.getInstance().toString());
        if (Model.getInstance().getMainSubject() != null && Model.getInstance().getDifficultyLevel() != null && Model.getInstance().getDifficultyLevel() != null) {
            allDropdownsSelected.set(true);
        }
    }

    /*================================= LISTENERS HANDLERS===================================== */
    private void onSubSubjectDropdownHandler(CustomDropdownMenu dropdownSubSubject) {
        String selectedSubText = dropdownSubSubject.getSelectedValue();

        // If Comprehension is selected , passage window will appear
        if (selectedSubText.contains("COMPREHENSION")) {
            passageTextWrapper.setVisible(true);
            passageBtn.setOnAction(event -> {
                Model.getInstance().getViewFactory().showPassageWindow();
            });
        } else {
            passageTextWrapper.setVisible(false);
        }
        // Cast the text back to ISubSubjectOptions
        ISubSubjectOptions selectedSub = SubSubjectOptionsEnglish.valueOf(selectedSubText); // Assuming enum values match text
        Model.getInstance().setSubSubject(selectedSub);
        checkAllDropdownsSelected();

    }

//    private void onWorksheetGenerateButtonClickedHandler() {
//
//        // Setting loading indicator's visibility to true
//        loadingIndicatorComponent.setVisible(true);
//
//        try {
//            // Handle the generated worksheet here
//            worksheetController.generateWorksheet(); // Use instance method
//
//        } catch (Exception e) {
//            // Show error to user, perhaps in a dialog
//            e.printStackTrace();
//        } finally {
//            // Setting loading indicator's visibility to false again
//            loadingIndicatorComponent.setVisible(false);
//        }
//    }

    private void onWorksheetGenerateButtonClickedHandler() {
        // Show the loading indicator
        loadingIndicatorComponent.setVisible(true);

        // Create a background task
        Task<Void> generateWorksheetTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Simulate worksheet generation (replace with actual logic)
                worksheetController.generateWorksheet();
                return null;
            }
        };

        // Handle success
        generateWorksheetTask.setOnSucceeded(event -> {
            loadingIndicatorComponent.setVisible(false);
            // Handle successful completion (e.g., show a success message)
        });

        // Handle failure
        generateWorksheetTask.setOnFailed(event -> {
            loadingIndicatorComponent.setVisible(false);
            Throwable error = generateWorksheetTask.getException();
            error.printStackTrace();
            // Show error to user
        });

        // Run the task in a background thread
        new Thread(generateWorksheetTask).start();
    }

}



