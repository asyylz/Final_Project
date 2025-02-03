package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetControllerTest;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsMaths;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.ViewFactory.UserMenuOptions;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
    public ImageView passageBtn;
    public Button clearSelectionBtn;
    public StackPane loadingIndicatorComponent;
    public Button testBtn;
    public ImageView stepOneTick;
    public ImageView stepTwoTick;
    public ImageView stepThreeTick;
    private BooleanProperty passageSectionRequired = new SimpleBooleanProperty();
    private BooleanProperty isGenerationCompleted = new SimpleBooleanProperty(false);

    //  private final WorksheetController worksheetController = new WorksheetController();
    private final WorksheetControllerTest worksheetController = new WorksheetControllerTest();

    // Initialize CustomDropdowns and populating their content
    CustomDropdownMenu dropdownMainSubject = new CustomDropdownMenu("MAIN SUBJECT", MainSubjectOptions.values());
    CustomDropdownMenu dropdownSubSubject = new CustomDropdownMenu("SUB SUBJECT");
    CustomDropdownMenu difficultyLevel = new CustomDropdownMenu("DIFFICULTY LEVEL", DifficultyLevelOptions.values());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Observer pattern
        worksheetController.addObserver(this);

        // Default we are setting indicator's visibility false
        loadingIndicatorComponent.setVisible(false);

        //Set font family Oswald
        Font.loadFont(GeneratorWindowController.class.getResourceAsStream("/Fonts/Oswald/Oswald-VariableFont_wght.ttf"), 12);
        generatorWindowParent.setStyle("-fx-font-family: 'Oswald'; -fx-font-size: 14px;");

        dropdownMainSubject.selectedProperty().set("MAIN");
        dropdownSubSubject.selectedProperty().set("SUB");
        difficultyLevel.selectedProperty().set("DIFF");


        // Add CustomDropdownMenus to the associated panes in layout
        wrapperCustomDropdownMenuOne.getChildren().add(dropdownMainSubject);
        wrapperCustomDropdownMenuTwo.getChildren().add(dropdownSubSubject);
        wrapperCustomDropdownMenuThree.getChildren().add(difficultyLevel);


        stepOneTick.visibleProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !dropdownMainSubject.selectedProperty().get().contains("MAIN"),
                        dropdownMainSubject.selectedProperty() // Binding to the custom property
                )
        );

        stepTwoTick.visibleProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !dropdownSubSubject.selectedProperty().get().contains("SUB"),
                        dropdownSubSubject.selectedProperty() // Binding to the custom property
                )
        );

        stepThreeTick.visibleProperty().bind(
                Bindings.createBooleanBinding(
                        () -> !difficultyLevel.selectedProperty().get().contains("DIFF"),
                        difficultyLevel.selectedProperty() // Binding to the custom property
                )
        );

        passageTextWrapper.visibleProperty().bind(passageSectionRequiredProperty());

        generateBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                Model.getInstance().getWorksheetPropertyForGeneration().mainSubjectProperty().get() == null ||
                                        Model.getInstance().getWorksheetPropertyForGeneration().subSubjectProperty().get() == null ||
                                        Model.getInstance().getWorksheetPropertyForGeneration().diffLevelProperty().get() == null ||
                                        (passageSectionRequired.get() &&
                                                (Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageContent() == null ||
                                                        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageContent().isEmpty() ||
                                                        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageTitle() == null ||
                                                        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageTitle().isEmpty())),
                        Model.getInstance().getWorksheetPropertyForGeneration().mainSubjectProperty(),
                        Model.getInstance().getWorksheetPropertyForGeneration().subSubjectProperty(),
                        Model.getInstance().getWorksheetPropertyForGeneration().diffLevelProperty(),
                        passageSectionRequired,
                        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().passageContentProperty(),
                        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().passageTitleProperty()
                ));

        //LISTENER
        // Set up model the connection mainSubjectDropdown
        dropdownMainSubject.setOnSelectionChanged(mainSubjectEvent -> {
            onMainSubjectDropdownHandler();
        });

        //LISTENER
        dropdownSubSubject.setOnSelectionChanged(subSubjectEvent -> onSubSubjectDropdownHandler());

        //LISTENER
        // Set up model the connection difficultyLevel
        difficultyLevel.setOnSelectionChanged(difficultyLevelEvent -> {
            String selectedDifficulty = difficultyLevel.getSelectedValue();
            DifficultyLevelOptions difficultyLevelOptions = DifficultyLevelOptions.valueOf(selectedDifficulty);
            Model.getInstance().getWorksheetPropertyForGeneration().diffLevelProperty().set(difficultyLevelOptions);

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
            clearSelectionsHandler();
            updatePassageSectionRequired();

        });
        // Test Listener TEST
        testBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            loadingIndicatorComponent.setVisible(true);
            try {
                worksheetController.generateWorksheet(); // calling worksheetcontroller
//                Model.getInstance().setWorksheetPropertyForGeneration(new WorksheetProperty());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });

    } // End of initialise

    /*================================= LISTENERS HANDLERS===================================== */
    private void onMainSubjectDropdownHandler() {
        String selectedMain = dropdownMainSubject.getSelectedValue();
        switch (selectedMain) {
            case "ENGLISH":
                dropdownSubSubject.setDropdownContent(SubSubjectOptionsEnglish.values());
                dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
                Model.getInstance().getWorksheetPropertyForGeneration().setMainSubject(MainSubjectOptions.ENGLISH);
                if (Model.getInstance().getWorksheetPropertyForGeneration().getSubSubject() != null) {
                    Model.getInstance().getWorksheetPropertyForGeneration().setSubSubject(null);
                    dropdownSubSubject.selectedProperty().set("SUB");
                    updatePassageSectionRequired();
                }
                break;
            case "MATHS":
                dropdownSubSubject.setDropdownContent(SubSubjectOptionsMaths.values());
                dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
                Model.getInstance().getWorksheetPropertyForGeneration().setMainSubject(MainSubjectOptions.MATHS);
                if (Model.getInstance().getWorksheetPropertyForGeneration().getSubSubject() != null) {
                    Model.getInstance().getWorksheetPropertyForGeneration().setSubSubject(null);
                    dropdownSubSubject.selectedProperty().set("SUB");
                    updatePassageSectionRequired();
                }
                break;
        }
    }

    private void onSubSubjectDropdownHandler() {
        String selectedSubText = dropdownSubSubject.getSelectedValue();
        // If Comprehension is selected , passage window will appear
        if ((selectedSubText.contains("COMPREHENSION")
                || selectedSubText.contains("CLOZE_TEST")
                || selectedSubText.contains("VOCABULARY")
                || selectedSubText.contains("SPAG")
                && Model.getInstance().getWorksheetPropertyForGeneration().getMainSubject().equals(MainSubjectOptions.ENGLISH))) {
            passageBtn.setOnMouseClicked(event -> {
                Model.getInstance().getViewFactory().showPassageWindow();
            });
            // Cast the text back to ISubSubjectOptions
            ISubSubjectOptions selectedSub = SubSubjectOptionsEnglish.valueOf(selectedSubText); // Assuming enum values match text
            Model.getInstance().getWorksheetPropertyForGeneration().setSubSubject(selectedSub);
            updatePassageSectionRequired();
        } else {
            // Cast the text back to ISubSubjectOptions
            ISubSubjectOptions selectedSub = SubSubjectOptionsMaths.valueOf(selectedSubText); // Assuming enum values match text
            Model.getInstance().getWorksheetPropertyForGeneration().setSubSubject(selectedSub);
            updatePassageSectionRequired();
        }
    }

    private void clearSelectionsHandler() {
        dropdownMainSubject.setMainButtonText("MAIN SUBJECT" + " ▼");
        dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
        difficultyLevel.setMainButtonText("DIFFICULTY LEVEL" + " ▼");
//        Model.getInstance().getWorksheetPropertyForGeneration().setMainSubject(null);
//        Model.getInstance().getWorksheetPropertyForGeneration().setDiffLevel(null);
//        Model.getInstance().getWorksheetPropertyForGeneration().setSubSubject(null);
//        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().setPassageContent(null);
//        Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().setPassageTitle(null);
//        Model.getInstance().getWorksheetPropertyForGeneration().removeQuestionsFromList(); // This belongs to model separately
        Model.getInstance().setWorksheetPropertyForGeneration(new WorksheetProperty());

        dropdownMainSubject.selectedProperty().set("MAIN");
        dropdownSubSubject.selectedProperty().set("SUB");
        difficultyLevel.selectedProperty().set("DIFF");

    }

    private void onWorksheetGenerateButtonClickedHandler() {
        // Setting loading indicator's visibility to true
        loadingIndicatorComponent.setVisible(true);

        // Handle the generated worksheet here
        worksheetController.generateWorksheet(); // This method already handles exceptions internally

        clearSelectionsHandler();
    }

    private void updatePassageSectionRequired() {
        // Ensure MainSubject is not null before checking its value
        if (Model.getInstance().getWorksheetPropertyForGeneration().getMainSubject() != null &&
                Model.getInstance().getWorksheetPropertyForGeneration().getMainSubject().equals(MainSubjectOptions.ENGLISH)) {

            boolean required = false;

            // Ensure SubSubject is not null before checking its value
            if (Model.getInstance().getWorksheetPropertyForGeneration().getSubSubject() != null) {
                required = switch (Model.getInstance().getWorksheetPropertyForGeneration().getSubSubject()) {
                    case SubSubjectOptionsEnglish.COMPREHENSION,
                         SubSubjectOptionsEnglish.CLOZE_TEST,
                         SubSubjectOptionsEnglish.VOCABULARY,
                         SubSubjectOptionsEnglish.SPAG -> true;
                    default -> false;
                };
            }
            passageSectionRequired.set(required);
        } else {
            passageSectionRequired.set(false);
        }
    }


    public BooleanProperty passageSectionRequiredProperty() {
        return passageSectionRequired;
    }

    @Override
    public void onWorksheetGenerated(WorksheetProperty worksheetProperty) {
        Model.getInstance().setWorksheetProperty(worksheetProperty);
      //  System.out.println(Model.getInstance().getWorksheetProperty().getId());
       // System.out.println(Model.getInstance().getWorksheetProperty());

        Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.WORKSHEET);


        Utils.notifyUser("Worksheet has been generated successfully!", "Worksheet Generated", "Success", Alert.AlertType.INFORMATION);
        loadingIndicatorComponent.setVisible(false);

        clearSelectionsHandler();

    }


}






