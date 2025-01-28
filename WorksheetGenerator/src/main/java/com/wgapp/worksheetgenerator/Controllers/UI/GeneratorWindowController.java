package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.Controllers.WorksheetControllerTest;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ListChangeListener;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ResourceBundle;


public class GeneratorWindowController implements Initializable, WorksheetControllerTest.WorksheetObserver {
    public AnchorPane generatorWindowParent;
    public Pane wrapperCustomDropdownMenuOne;
    public Pane wrapperCustomDropdownMenuTwo;
    public Pane wrapperCustomDropdownMenuThree;
    public Button generateBtn;
    public HBox passageTextWrapper;
    public Button passageBtn;
    public Button clearSelectionBtn;
    public StackPane loadingIndicatorComponent;
    public Button testBtn;
    private BooleanProperty allDropdownsSelected = new SimpleBooleanProperty(false);

    private final WorksheetController worksheetController = new WorksheetController();
    private final WorksheetControllerTest worksheetControllerTest = new WorksheetControllerTest();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Observer pattern
        worksheetControllerTest.addObserver(this);

        // Default we are setting indicator's visibility false
        loadingIndicatorComponent.setVisible(false);

        //Set font family Oswald
        Font.loadFont(GeneratorWindowController.class.getResourceAsStream("/Fonts/Oswald/Oswald-VariableFont_wght.ttf"), 12);
        generatorWindowParent.setStyle("-fx-font-family: 'Oswald'; -fx-font-size: 14px;");


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
            generateButtonActivationControl();
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
            generateButtonActivationControl();
        });

        //LISTENER
        // generateBtn listens for click event to generate worksheet
        generateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            onWorksheetGenerateButtonClickedHandler();
        });

        //LISTENER
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

        //LISTENER
        clearSelectionBtn.setOnMouseClicked(event -> {
            dropdownMainSubject.setMainButtonText("MAIN SUBJECT" + " ▼");
            dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
            difficultyLevel.setMainButtonText("DIFFICULTY LEVEL" + " ▼");
            allDropdownsSelected.set(false);
            Model.getInstance().setMainSubject(null);
            Model.getInstance().setDifficultyLevel(null);
            Model.getInstance().setSubSubject(null);
            Model.getInstance().setPassageContent(null);
            Model.getInstance().setPassageTitle(null);
            Model.getInstance().removeQuestionsFromList();

        });

        // Listener for passage field
        Model.getInstance().getPassageContentProperty().addListener((observable, oldValue, newValue) -> generateButtonActivationControl());

        // Listener for title field
        Model.getInstance().getPassageTitleProperty().addListener((observable, oldValue, newValue) -> generateButtonActivationControl());

        // Listener for question list
        Model.getInstance().getQuestionTypeListProperty().addListener((ListChangeListener<String>) change -> generateButtonActivationControl());

        /* Initially disable the generate button,
        Bind generate button's disable property to the inverse of allDropdownsSelected ,
        if passage section required accordingly will be active */
        generateBtn.disableProperty().bind(allDropdownsSelected.not());


        // Test Listener TEST
        testBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            loadingIndicatorComponent.setVisible(true);
            try {
                worksheetControllerTest.generateWorksheet(); // calling worksheetcontroller
                //System.out.println("from ui" + worksheet.getPassage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

    } // End of initialise

    private void generateButtonActivationControl() {
        boolean areDropdownsSelected = Model.getInstance().getMainSubject() != null
                && Model.getInstance().getDifficultyLevel() != null
                && Model.getInstance().getSubSubject() != null;

        boolean isPassageSectionContentSet = !Model.getInstance().getPassageContent().isEmpty()
                && !Model.getInstance().getPassageTitle().isEmpty()
                && !Model.getInstance().getQuestionTypeList().isEmpty();

        allDropdownsSelected.set(areDropdownsSelected && (isPassageSectionRequired() && isPassageSectionContentSet));
    } // End of checkAllDropdownsSelected

    private boolean isPassageSectionRequired() {
        switch (Model.getInstance().getSubSubject().get()) {
            case SubSubjectOptionsEnglish.COMPREHENSION, SubSubjectOptionsEnglish.CLOZETEST:
                return true;
            default:
                return false;
        }
    } // end of isPassageSectionRequired


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
        generateButtonActivationControl();
    }

    private void onWorksheetGenerateButtonClickedHandler() {

        // Setting loading indicator's visibility to true
        loadingIndicatorComponent.setVisible(true);

        try {
            // Handle the generated worksheet here
            worksheetControllerTest.generateWorksheet(); // Use instance method
        } catch (Exception e) {
            // Show error to user, perhaps in a dialog
            e.printStackTrace();
        } finally {
            // Setting loading indicator's visibility to false again
            loadingIndicatorComponent.setVisible(false);
        }
    }

    @Override
    public void onWorksheetGenerated(Worksheet worksheet) {
        Model.getInstance().setWorksheet(worksheet);
        Model.getInstance().getViewFactory().showWorksheetWindowWithPassage();
    }

}






