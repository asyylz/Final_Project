package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsMaths;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import com.wgapp.worksheetgenerator.ViewFactory.UserMenuOptions;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
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
import java.util.concurrent.CompletableFuture;

public class GeneratorWindowController implements Initializable, WorksheetController.WorksheetObserver {
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
    public AnchorPane diffLevelWrapper;
    private BooleanProperty passageSectionRequired = new SimpleBooleanProperty(false);

    private final WorksheetController worksheetController = new WorksheetController();

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


        Model.getInstance().worksheetPropertyForGenerationProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println(newValue.mainSubjectProperty().get());
            System.out.println(Model.getInstance().getWorksheetPropertyForGeneration().mainSubjectProperty().get());
            System.out.println(Model.getInstance().getWorksheetPropertyForGeneration().subSubjectProperty().get());
            System.out.println(Model.getInstance().getWorksheetPropertyForGeneration().diffLevelProperty().get());
            System.out.println(Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().passageContentProperty());
            System.out.println(Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().passageTitleProperty());
            System.out.println(passageSectionRequired);
        });


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

    private void onWorksheetGenerateButtonClickedHandler() {
        // Setting loading indicator's visibility to true
        loadingIndicatorComponent.setVisible(true);
        worksheetController.generateWorksheet(Model.getInstance().getWorksheetPropertyForGeneration());

    }

    private void clearSelectionsHandler() {
        dropdownMainSubject.setMainButtonText("MAIN SUBJECT" + " ▼");
        dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
        difficultyLevel.setMainButtonText("DIFFICULTY LEVEL" + " ▼");
        Model.getInstance().setWorksheetPropertyForGeneration(new WorksheetProperty());

        dropdownMainSubject.selectedProperty().set("MAIN");
        dropdownSubSubject.selectedProperty().set("SUB");
        difficultyLevel.selectedProperty().set("DIFF");

        updatePassageSectionRequired();

        loadingIndicatorComponent.setVisible(false);

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
        Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.WORKSHEET);
        clearSelectionsHandler();
        Model.getInstance().getWorksheetProperty().setUserProperty(Model.getInstance().getUserProperty()); // since new worksheet created we re attach user data  again
    }

    @Override
    public void onWorksheetDeleted() {

    }

    @Override
    public void onWorksheetFound(WorksheetProperty worksheetProperty) {

    }

    @Override
    public void onWorksheetsListed(ListProperty<WorksheetProperty> worksheetPropertyList) {

    }

}






