package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Enums.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLOutput;
import java.util.ResourceBundle;

public class PassageWindowController implements Initializable {
    public AnchorPane passageWrapper;
    public Button clearTextBtn;
    public VBox wrapperBtns;
    public Button closeBtn;
    public CheckBox inferential;
    public CheckBox literal;
    public CheckBox summarizing;
    public CheckBox vocabulary;
    public CheckBox characterAnalysis;
    public CheckBox authorPurpose;
    public CheckBox criticalThinking;
    public CheckBox structure;
    public CheckBox toneAndMood;
    public TextArea readingPassage;
    public VBox questionTypes;
    public TextField passageTitle;
    public Button beautifyBtn;
    private BooleanProperty doesQuestionTypesRequire = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doesQuestionTypesRequire.bind(Model.getInstance().getWorksheetPropertyForGeneration().subSubjectProperty().isEqualTo(SubSubjectOptionsEnglish.COMPREHENSION));

        // Set the initial content of the TextArea to the value stored in the model
        readingPassage.setText(Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageContent());
        // Set the initial title of the passage to the value stored in the model
        passageTitle.setText(Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageTitle());
        // Listener collection
        addListener();

        questionTypes.visibleProperty().bind(doesQuestionTypesRequire);

        //readingPassage.setStyle("-fx-text-alignment: justify-center;");


    } // End of initialize

    private void addListener() {
        // beautifyBtn.setOnAction(e -> onBeautifyButtonClicked());

        //Clear passage and title for new values
        clearTextBtn.setOnAction(e -> {
            readingPassage.clear();
            passageTitle.clear();
            toneAndMood.setSelected(false);
            structure.setSelected(false);
            inferential.setSelected(false);
            literal.setSelected(false);
            summarizing.setSelected(false);
            vocabulary.setSelected(false);
            characterAnalysis.setSelected(false);
            authorPurpose.setSelected(false);
            criticalThinking.setSelected(false);
        });

        readingPassage.textProperty().addListener((observable, oldValue, newValue) -> {
            //  System.out.println(newValue);
            Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().setPassageContent(newValue);

        });

        passageTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().setPassageTitle(newValue);
        });

        // Listener for closeBtn
        closeBtn.setOnAction(e -> {
            onCloseBtnClickedHandler();
        });

        //Listens questions types list in model to update ui
        Model.getInstance().getWorksheetPropertyForGeneration().questionTypeListProperty().addListener((observable, oldValue, newValue) -> {
            onComprehensionQuestionTypesListener();
        });

        beautifyBtn.setOnAction(e -> {
            onBeautifyButtonClicked();
        });

        onComprehensionQuestionTypesListener();
        populateCheckBoxes();

    }

    /*================================= CHECKBOX POPULATION ===================================== */
    private void populateCheckBoxes() {
        inferential.setUserData(ComprehensionQuestionTypes.INFERENTIAL);
        literal.setUserData(ComprehensionQuestionTypes.LITERAL);
        summarizing.setUserData(ComprehensionQuestionTypes.SUMMARIZING);
        vocabulary.setUserData(ComprehensionQuestionTypes.VOCABULARY);
        characterAnalysis.setUserData(ComprehensionQuestionTypes.CHARACTER_ANALYSIS);
        toneAndMood.setUserData(ComprehensionQuestionTypes.TONE_AND_MOOD);
        structure.setUserData(ComprehensionQuestionTypes.STRUCTURE);
        authorPurpose.setUserData(ComprehensionQuestionTypes.AUTHORS_PURPOSE);
        criticalThinking.setUserData(ComprehensionQuestionTypes.CRITICAL_THINKING);
    }

    /*================================= HANDLERS ===================================== */
    private void onCloseBtnClickedHandler() {

        // Get the current stage
        Stage currentStage = (Stage) closeBtn.getScene().getWindow();

        boolean isTitleEmpty = Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageTitle() == null;
        boolean isPassageEmpty = Model.getInstance().getWorksheetPropertyForGeneration().passageProperty().getPassageContent() == null;
        boolean isQuestionListEmpty = Model.getInstance().getWorksheetPropertyForGeneration().getQuestionTypeList().isEmpty();

        if (doesQuestionTypesRequire.get() && isQuestionListEmpty) {
            Model.getInstance().getViewFactory().showModalWindow(
                    "You haven’t chosen any question type. All types will be included."
            );
        } else if (isPassageEmpty) {
            Model.getInstance().getViewFactory().showModalWindow(
                    "You haven’t set passage, please add a reading passage."
            );
        } else if (isTitleEmpty) {
            Model.getInstance().getViewFactory().showModalWindow(
                    "You haven’t set passage title, please add a reading passage title."
            );
        } else {
            Model.getInstance().getViewFactory().closeStage(currentStage);
        }
    }


    public void onComprehensionQuestionTypesListener() {
        // Iterate through all children in the VBox (questionTypes)
        for (int j = 0; j < questionTypes.getChildren().size(); j++) {
            Node node = questionTypes.getChildren().get(j);

            if (node instanceof CheckBox) {
                CheckBox checkbox = (CheckBox) node;
                String title = checkbox.getText();

                // Create a BooleanProperty for binding
                BooleanProperty isTrueProperty = Utils.hasQuestionType(title);

                // Bind the CheckBox's selectedProperty bidirectionally to the BooleanProperty
                checkbox.selectedProperty().bindBidirectional(isTrueProperty);

                checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                    ComprehensionQuestionTypes questionType = (ComprehensionQuestionTypes) checkbox.getUserData();
                    if (newValue) {
                        // Add to list if not already present
                        if (!Model.getInstance().getWorksheetPropertyForGeneration().getQuestionTypeList().contains(questionType)) {
                            Model.getInstance().getWorksheetPropertyForGeneration().getQuestionTypeList().add(questionType);
                        }
                    } else {
                        // Remove from the list if unchecked
                        Model.getInstance().getWorksheetPropertyForGeneration().getQuestionTypeList().remove(questionType);

                    }
                });

            }
        }
    }

    /*================================= BEAUTIFY METHODS ===================================== */
    private void onBeautifyButtonClicked() {
        String text = readingPassage.getText();

        // Beautify the text by rearranging and formatting
        String beautifiedText = beautifyText(text);

        // Set the beautified text back into the TextArea
        readingPassage.setText(beautifiedText);
    }

    // Beautify text by wrapping long lines and adding spacing
    private String beautifyText(String text) {
        // Example: Trim excess spaces and wrap long lines
        text = text.trim(); // Trim leading and trailing spaces
        text = text.replaceAll("\\s+", " "); // Replace multiple spaces with a single space
        text = text.replaceAll("\\n+", " "); // Replace unnecessary line breaks with a single space
        // Ensure dialogue (starting with a quote) begins as a block on a new line
//        text = text.replaceAll("(?<!\\w)(?=[\"“”])", "\n"); // New line before opening quote (not after a word)
//        text = text.replaceAll("(?<=[\"“”])(?=\\s*[A-Z])", "\n"); // New line after closing quote IF followed by capital letter (new sentence)


//        StringBuilder beautified = new StringBuilder();
//        int maxLineLength = 95;
//        int currentLength = 0;
//
//        for (String word : text.split(" ")) {
//            if (currentLength + word.length() > maxLineLength) {
//                beautified.append("\n");  // Add a line break when max length is exceeded
//                currentLength = 0;
//            }
//            beautified.append(word).append(" ");
//            currentLength += word.length() + 1;
//        }
//
//        return beautified.toString();
        return text;
    }

}
