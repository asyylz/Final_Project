package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PassageWindowController implements Initializable {
    public AnchorPane passageWrapper;
    public Button beautifyBtn;
    public Button confirmBtn;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
        onComprehensionQuestionTypesListener();
        populateCheckBoxes();
    }

    private void addListener() {
        beautifyBtn.setOnAction(e -> onBeautifyButtonClicked());
        clearTextBtn.setOnAction(e -> onClearButtonClicked());
        readingPassage.textProperty().addListener((observable, oldValue, newValue) -> {
            Model.getInstance().setPassageContent(readingPassage.getText());
        });

        //confirmButton listens for click to set passage to Model
        confirmBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Model.getInstance().setPassageContent(readingPassage.getText()); // Passage
            Model.getInstance().setPassageTitle(passageTitle.getText()); // Title

            confirmBtn.setDisable(true);
            readingPassage.setDisable(true);
        });

        //Closes passageWindow onClick of closeBtn
        closeBtn.setOnAction(e -> {
            Stage currentStage = (Stage) closeBtn.getScene().getWindow();
            Model.getInstance().getViewFactory().closeStage(currentStage);
        });

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

    /*================================= LISTENERS ===================================== */
    private void onComprehensionQuestionTypesListener() {
        for (int i = 0; i < questionTypes.getChildren().size(); i++) {

            // Each VBox contains CheckBox(es), add listener to them
            for (int j = 0; j < questionTypes.getChildren().size(); j++) {
                Node node = questionTypes.getChildren().get(j);

                if (node instanceof CheckBox) {
                    CheckBox checkbox = (CheckBox) node;

                    // Add a listener to the selected property of the CheckBox
                    checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                        // Ensure the newValue is valid (check if the checkbox is selected)
                        if (newValue != null) {
                            ComprehensionQuestionTypes questionType = (ComprehensionQuestionTypes) checkbox.getUserData();
                            // System.out.println("from CheckBox: " + questionType);

                            // Add the selected question type to the list if it’s checked
                            if (newValue) {
                                // Only add if not already in the list
                                if (!Model.getInstance().getQuestionTypeList().contains(questionType)) {
                                    Model.getInstance().getQuestionTypeList().add(questionType); // Add to list
                                    System.out.println("List" + Model.getInstance().getQuestionTypeList());
                                }
                            } else {
                                // Remove the question type from the list if it’s unchecked
                                Model.getInstance().getQuestionTypeList().remove(questionType);
                                System.out.println("List" + Model.getInstance().getQuestionTypeList());
                            }
                        }
                    });
                }
            }
        }
    }

    private void onClearButtonClicked() {
        readingPassage.clear();
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
        //text = text.replaceAll("(?<=\\w)(?=\\p{Punct})", " "); // Add space before punctuation if needed

        StringBuilder beautified = new StringBuilder();
        int maxLineLength = 95;
        int currentLength = 0;

        for (String word : text.split(" ")) {
            if (currentLength + word.length() > maxLineLength) {
                beautified.append("\n");  // Add a line break when max length is exceeded
                currentLength = 0;
            }
            beautified.append(word).append(" ");
            currentLength += word.length() + 1;
        }

        return beautified.toString();
    }


}
