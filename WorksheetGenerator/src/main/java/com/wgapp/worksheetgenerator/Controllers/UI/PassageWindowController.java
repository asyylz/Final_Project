package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.SubSubjectOptionsEnglish;
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
import java.util.ResourceBundle;

public class PassageWindowController implements Initializable {
    public AnchorPane passageWrapper;
    public Button beautifyBtn;
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
    private BooleanProperty doesQuestionTypesRequire = new SimpleBooleanProperty();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        doesQuestionTypesRequire.bind(Model.getInstance().getSubSubject().isEqualTo(SubSubjectOptionsEnglish.COMPREHENSION));

        // Set the initial content of the TextArea to the value stored in the model
        readingPassage.setText(Model.getInstance().getPassageContent());
        // Set the initial title of the passage to the value stored in the model
        passageTitle.setText(Model.getInstance().getPassageTitle());
        // Listener collection
        addListener();

        questionTypes.visibleProperty().bind(doesQuestionTypesRequire);


    } // End of initialize

    private void addListener() {
        beautifyBtn.setOnAction(e -> onBeautifyButtonClicked());

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
            Model.getInstance().setPassageContent(readingPassage.getText());
        });

        passageTitle.textProperty().addListener((observable, oldValue, newValue) -> {
            Model.getInstance().setPassageTitle(newValue);
        });

        // Listener for closeBtn
        closeBtn.setOnAction(e -> {
            onCloseBtnClickedHandler();
        });

        //Listens questions types list in model to update ui
        Model.getInstance().getQuestionTypeListProperty().addListener((observable, oldValue, newValue) -> {
            onComprehensionQuestionTypesListener();
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
        // We are getting current stage
        Stage currentStage = (Stage) closeBtn.getScene().getWindow();
        if (Model.getInstance().getQuestionTypeList().isEmpty() && doesQuestionTypesRequire.get()) {
            Model.getInstance().getViewFactory().showModalWindow("You haven’t chosen any question type." +
                    " All types will be included.");
        } else if (Model.getInstance().getPassageContent().isEmpty()) {
            Model.getInstance().getViewFactory().showModalWindow("You haven’t set passage, please add a reading passage.");

        } else if (Model.getInstance().getPassageTitle().isEmpty()) {
            Model.getInstance().getViewFactory().showModalWindow("You haven’t set passage title, please add a reading passage title.");
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
                        if (!Model.getInstance().getQuestionTypeList().contains(questionType)) {
                            Model.getInstance().getQuestionTypeList().add(questionType);
                        }
                    } else {
                        // Remove from the list if unchecked
                        Model.getInstance().getQuestionTypeList().remove(questionType);
                        // System.out.println("Removed from List: " + questionType);
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


/*================================= LISTENERS ===================================== */
//
//    public void onComprehensionQuestionTypesListener() {
//
//        //  for (int i = 0; i < questionTypes.getChildren().size(); i++) {
//
//        // VBox contains CheckBox(es), add listener to them
//        for (int j = 0; j < questionTypes.getChildren().size(); j++) {
//            Node node = questionTypes.getChildren().get(j);
//
//            if (node instanceof CheckBox) {
//
//                CheckBox checkbox = (CheckBox) node;
//                String title = checkbox.getText();
//
//                // First checking which types of questions has been selected previously
//
//                isTrue = UtilForStrings.hasQuestionType(title);
//                System.out.println("istrue" +isTrue);
//
////                if (isTrue) {
////                    checkbox.setSelected(true);
////                }
//                checkbox.selectedProperty().bindBidirectional(isTrue);
//
//                // Add a listener to the selected property of the CheckBox
//                checkbox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//                    // Ensure the newValue is valid (check if the checkbox is selected)
//                    if (newValue != null) {
//                        ComprehensionQuestionTypes questionType = (ComprehensionQuestionTypes) checkbox.getUserData();
//
//                        // Add the selected question type to the list if it’s checked
//                        if (newValue) {
//                            // Only add if not already in the list
//                            if (!Model.getInstance().getQuestionTypeList().contains(questionType)) {
//                                Model.getInstance().getQuestionTypeList().add(questionType); // Add to list
//                                System.out.println("List" + Model.getInstance().getQuestionTypeList());
//                            }
//                        } else {
//                            // Remove the question type from the list if it’s unchecked
//                            Model.getInstance().getQuestionTypeList().remove(questionType);
//                            System.out.println("List" + Model.getInstance().getQuestionTypeList());
//                        }
//                    }
//                });
//            }
//        }
//        //}
//    }