package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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
    private BooleanProperty allDropdownsSelected = new SimpleBooleanProperty(false);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
        // Set up model the connection subSubjectDropdown
        dropdownSubSubject.setOnSelectionChanged(subSubjectEvent -> onSubSubjectDropdownListener(dropdownSubSubject));

        // Set up model the connection difficultyLevel
        difficultyLevel.setOnSelectionChanged(difficultyLevelEvent -> {
            String selectedDifficulty = difficultyLevel.getSelectedValue();
            DifficultyLevelOptions difficultyLevelOptions = DifficultyLevelOptions.valueOf(selectedDifficulty);
            Model.getInstance().setDifficultyLevel(difficultyLevelOptions);
            checkAllDropdownsSelected();
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

    }

    private void checkAllDropdownsSelected() {
        System.out.println(Model.getInstance().toString());
        if (Model.getInstance().getMainSubject() != null && Model.getInstance().getDifficultyLevel() != null && Model.getInstance().getDifficultyLevel() != null) {
            allDropdownsSelected.set(true);
        }
    }

    /*================================= LISTENERS ===================================== */
    private void onSubSubjectDropdownListener(CustomDropdownMenu dropdownSubSubject) {
        String selectedSubText = dropdownSubSubject.getSelectedValue();
        //System.out.println("Sub-Subject Selected: " + selectedSubText);

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


    /*================================= LISTENERS ===================================== */
    private void onWorksheetGenerateButtonClicked() {
        if (Model.getInstance().getQuestionTypeList().isEmpty()) {
            // Get the modal window view as a parent/root from the ViewFactory
            VBox modalWindowParent = Model.getInstance().getViewFactory().getModalWindowView();

            // We are gettin current window x and y coordinates
            Stage currentStage = (Stage) generatorWindowParent.getScene().getWindow();
            System.out.println(currentStage.getTitle());
            double x = currentStage.getX();
            double y = currentStage.getY();

            ScaleTransition st = new ScaleTransition(Duration.millis(500), modalWindowParent);
            st.setInterpolator(Interpolator.EASE_BOTH);
            st.setFromX(0);
            st.setFromY(0);
            st.setToX(1);
            st.setToY(1);

            // Detach from any existing parent
            if (modalWindowParent.getScene() != null) {
                modalWindowParent.getScene().setRoot(new VBox()); // Replace with an empty placeholder
            }

            //  Set up the modal window stage
            Stage modalStage = new Stage();
            modalStage.setTitle("Warning");
            modalStage.initStyle(StageStyle.TRANSPARENT);

            Scene modalScene = new Scene(modalWindowParent, 400, 200);
            modalScene.setFill(Color.TRANSPARENT);

            modalStage.initModality(Modality.APPLICATION_MODAL); // Blocks interaction with other windows

            // Make the modal non-resizable
            modalStage.setResizable(false);
            modalStage.setScene(modalScene);

            // Set the stage in the controller for proper handling
            // Set the stage in the controller for proper handling
            ModalWindowController controller = (ModalWindowController) modalWindowParent.getProperties().get("controller");
            if (controller != null) {
                controller.setStage(modalStage);
            } else {
                System.err.println("Controller not found for the modal window!");
            }

            // Show the modal stage
            modalStage.showAndWait();

            // Apply the scale transition
            st.play();


        } else {
            WorksheetController.generateWorksheet();
        }
    }


}
