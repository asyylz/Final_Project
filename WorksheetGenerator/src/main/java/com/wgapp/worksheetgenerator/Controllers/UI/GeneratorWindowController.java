package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Components.CustomDropdownMenu;
import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.Controllers.WorksheetControllerTest;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    public Text userNameAfterLogin;
    public ImageView avatar;
    private BooleanProperty passageSectionRequired = new SimpleBooleanProperty();

    private final WorksheetController worksheetController = new WorksheetController();
    private final WorksheetControllerTest worksheetControllerTest = new WorksheetControllerTest();

    // Initialize CustomDropdowns and populating their content
    CustomDropdownMenu dropdownMainSubject = new CustomDropdownMenu("MAIN SUBJECT", MainSubjectOptions.values());
    CustomDropdownMenu dropdownSubSubject = new CustomDropdownMenu("SUB SUBJECT");
    CustomDropdownMenu difficultyLevel = new CustomDropdownMenu("DIFFICULTY LEVEL", DifficultyLevelOptions.values());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Observer pattern
        worksheetControllerTest.addObserver(this);

        // Default we are setting indicator's visibility false
        loadingIndicatorComponent.setVisible(false);

        //Set font family Oswald
        Font.loadFont(GeneratorWindowController.class.getResourceAsStream("/Fonts/Oswald/Oswald-VariableFont_wght.ttf"), 12);
        generatorWindowParent.setStyle("-fx-font-family: 'Oswald'; -fx-font-size: 14px;");


        // Add CustomDropdownMenus to the associated panes in layout
        wrapperCustomDropdownMenuOne.getChildren().add(dropdownMainSubject);
        wrapperCustomDropdownMenuTwo.getChildren().add(dropdownSubSubject);
        wrapperCustomDropdownMenuThree.getChildren().add(difficultyLevel);

        stepOneTick.visibleProperty().bind(Model.getInstance().getMainSubjectProperty().isNotNull());
        stepTwoTick.visibleProperty().bind(Model.getInstance().getSubSubjectProperty().isNotNull());
        stepThreeTick.visibleProperty().bind(Model.getInstance().getDifficultyLevelProperty().isNotNull());
        passageTextWrapper.visibleProperty().bind(passageSectionRequiredProperty());
        // User name near avatar
        userNameAfterLogin.setText(Model.getInstance().getUserName());


        generateBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                Model.getInstance().getMainSubjectProperty().get() == null ||
                                        Model.getInstance().getSubSubjectProperty().get() == null ||
                                        Model.getInstance().getDifficultyLevel() == null ||
                                        (passageSectionRequired.get() &&
                                                (Model.getInstance().getPassageContentProperty().get() == null ||
                                                        Model.getInstance().getPassageContentProperty().get().isEmpty() ||
                                                        Model.getInstance().getPassageTitleProperty().get() == null ||
                                                        Model.getInstance().getPassageTitleProperty().get().isEmpty())),
                        Model.getInstance().getMainSubjectProperty(),
                        Model.getInstance().getSubSubjectProperty(),
                        Model.getInstance().getDifficultyLevelProperty(),
                        passageSectionRequired,
                        Model.getInstance().getPassageContentProperty(),
                        Model.getInstance().getPassageTitleProperty()
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
            Model.getInstance().setDifficultyLevel(difficultyLevelOptions);
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
                worksheetControllerTest.generateWorksheet(); // calling worksheetcontroller
                //System.out.println("from ui" + worksheet.getPassage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        avatar.setOnMouseClicked(event -> {
            BooleanProperty isLogout = Utils.notifyUser("Would you like to log out ?", "Logout request", "Logout", Alert.AlertType.CONFIRMATION);

            if (isLogout.get()) {
                Stage currentStage = (Stage) avatar.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(currentStage);
                Model.getInstance().setUserName(null);
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
                Model.getInstance().setMainSubject(MainSubjectOptions.ENGLISH);
                if (Model.getInstance().getSubSubjectProperty().get() != null) {
                    updatePassageSectionRequired();
                    Model.getInstance().getSubSubjectProperty().set(null);
                }
                break;
            case "MATHS":
                dropdownSubSubject.setDropdownContent(SubSubjectOptionsMaths.values());
                dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
                Model.getInstance().setMainSubject(MainSubjectOptions.MATHS);
                if (Model.getInstance().getSubSubjectProperty().get() != null) {
                    Model.getInstance().getSubSubjectProperty().set(null);
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
                && Model.getInstance().getMainSubjectProperty().get().equals(MainSubjectOptions.ENGLISH))) {
            passageBtn.setOnMouseClicked(event -> {
                Model.getInstance().getViewFactory().showPassageWindow();
            });
            // Cast the text back to ISubSubjectOptions
            ISubSubjectOptions selectedSub = SubSubjectOptionsEnglish.valueOf(selectedSubText); // Assuming enum values match text
            Model.getInstance().setSubSubject(selectedSub);
            updatePassageSectionRequired();
        } else {
            // Cast the text back to ISubSubjectOptions
            ISubSubjectOptions selectedSub = SubSubjectOptionsMaths.valueOf(selectedSubText); // Assuming enum values match text
            Model.getInstance().setSubSubject(selectedSub);
            updatePassageSectionRequired();
        }
    }

    private void clearSelectionsHandler() {
        dropdownMainSubject.setMainButtonText("MAIN SUBJECT" + " ▼");
        dropdownSubSubject.setMainButtonText("SUB SUBJECT" + " ▼");
        difficultyLevel.setMainButtonText("DIFFICULTY LEVEL" + " ▼");
        Model.getInstance().setMainSubject(null);
        Model.getInstance().setDifficultyLevel(null);
        Model.getInstance().setSubSubject(null);
        Model.getInstance().setPassageContent(null);
        Model.getInstance().setPassageTitle(null);
        Model.getInstance().removeQuestionsFromList();
    }

    private void onWorksheetGenerateButtonClickedHandler() {
        // Setting loading indicator's visibility to true
        loadingIndicatorComponent.setVisible(true);

        // Handle the generated worksheet here
        worksheetControllerTest.generateWorksheet(); // This method already handles exceptions internally

        clearSelectionsHandler();
    }

    private void updatePassageSectionRequired() {
        // Ensure MainSubject is not null before checking its value
        if (Model.getInstance().getMainSubjectProperty().get() != null &&
                Model.getInstance().getMainSubjectProperty().get().equals(MainSubjectOptions.ENGLISH)) {

            boolean required = false;

            // Ensure SubSubject is not null before checking its value
            if (Model.getInstance().getSubSubjectProperty().get() != null) {
                required = switch (Model.getInstance().getSubSubjectProperty().get()) {
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
    public void onWorksheetGenerated(Worksheet worksheet) {

        Model.getInstance().setWorksheet(worksheet);
        Model.getInstance().getViewFactory().showWorksheetWindowWithPassage();

        // Show success message
        Utils.notifyUser("Worksheet has been generated successfully!", "Worksheet Generated", "Success", Alert.AlertType.INFORMATION);
//        Alert alert = new Alert();
//        alert.setTitle();
//        alert.setHeaderText();
//        alert.setContentText();
//        alert.show();
//
//        // Create a PauseTransition to wait for 5 seconds
//        PauseTransition pause = new PauseTransition(Duration.seconds(2));
//
//        // Set an action to close the alert when the time is up
//        pause.setOnFinished(e -> alert.close());
//
//        // Start the pause transition
//        pause.play();

        loadingIndicatorComponent.setVisible(false);
    }

}






