package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.Question;
import com.wgapp.worksheetgenerator.ModelsUI.QuestionProperty;
import com.wgapp.worksheetgenerator.ModelsUI.UserAnswer;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import java.net.URL;
import java.util.ResourceBundle;


public class QuestionComponentController implements Initializable {

    public VBox choicesWrapper;
    public VBox questionWrapper;
    public Text questionText;
    public HBox choiceWrapper1;
    public RadioButton choiceRadioBtn1;
    public HBox choiceWrapper2;
    public Text choiceText1;
    public RadioButton choiceRadioBtn2;
    public Text choiceText2;
    public HBox choiceWrapper3;
    public RadioButton choiceRadioBtn3;
    public Text choiceText3;
    public HBox choiceWrapper4;
    public RadioButton choiceRadioBtn4;
    public Text choiceText4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    } // End of Initializer


    public void setQuestion(QuestionProperty question, int questionIndex, ScrollPane rigthScrollPane) {
        // Set the question text
        questionText.setText(question.getQuestionText());

        // Listen to the width of the ScrollPane dynamically
        rigthScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue() - 90; // Adjust for padding or margins

            // Update the questionWrapper width and wrapping widths
            questionWrapper.setPrefWidth(newWidth);
            questionText.setWrappingWidth(newWidth - 20); // Wrapping width of the question
            choiceText1.setWrappingWidth(newWidth - 20);  // Adjust wrapping for choices
            choiceText2.setWrappingWidth(newWidth - 20);
            choiceText3.setWrappingWidth(newWidth - 20);
            choiceText4.setWrappingWidth(newWidth - 20);
        });

        // Set the choice texts
        for (int i = 0; i < choicesWrapper.getChildren().size(); i++) {
            Node node = choicesWrapper.getChildren().get(i);

            if (node instanceof HBox) {
                HBox choiceBoxSingle = (HBox) node;

                // Set the text for each choice in the HBox
                Text choiceText = (Text) choiceBoxSingle.getChildren().get(1);
                choiceText.setText(question.getChoices().get(i).getChoiceText());

                // Update wrapping width when ScrollPane resizes
                rigthScrollPane.widthProperty().addListener((observable, oldValue, newValue) -> {
                    double newWidth = newValue.doubleValue() - 90; // Adjust for padding or margins
                    choiceText.setWrappingWidth(newWidth - 20);
                });
            }
        }
        // Attach the question ID to the ToggleGroup listener
        onSelectedRadioBtnListener(questionIndex);
    } // End of setQuestion

    private void onSelectedRadioBtnListener(int questionIndex) {
        ToggleGroup tg = new ToggleGroup();
        choiceRadioBtn1.setToggleGroup(tg);
        choiceRadioBtn2.setToggleGroup(tg);
        choiceRadioBtn3.setToggleGroup(tg);
        choiceRadioBtn4.setToggleGroup(tg);

        tg.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton rb = (RadioButton) tg.getSelectedToggle();
                String selectedChoice = "";

                // Determine which choice was selected
                switch (rb.getId()) {
                    case "choiceRadioBtn1":
                        selectedChoice = "A";
                        break;
                    case "choiceRadioBtn2":
                        selectedChoice = "B";
                        break;
                    case "choiceRadioBtn3":
                        selectedChoice = "C";
                        break;
                    case "choiceRadioBtn4":
                        selectedChoice = "D";
                        break;
                }

                // If a choice is selected, update or add the answer
                if (!selectedChoice.isEmpty()) {
                    // Check if there's already an answer for the question
                    boolean answerUpdated = false;

                    for (UserAnswer userAnswer : Model.getInstance().getUserAnswersListProperty()) {
                        if (userAnswer.getQuestionIndex() == questionIndex) {
                            // Update the existing answer
                            userAnswer.setAnswer(selectedChoice);
                            answerUpdated = true;
                            System.out.println("Updated answer for question " + questionIndex + ": " + selectedChoice);
                            break;
                        }
                    }

                    if (!answerUpdated) {
                        // No existing answer for this question, so add a new one
                        Model.getInstance().getUserAnswersListProperty().add(new UserAnswer(selectedChoice, questionIndex));
                        System.out.println("Saved answer for question " + questionIndex + ": " + selectedChoice);
                    }
                }

            }
        });
    }

    public void clearSelection() {
        choiceRadioBtn1.setSelected(false);
        choiceRadioBtn2.setSelected(false);
        choiceRadioBtn3.setSelected(false);
        choiceRadioBtn4.setSelected(false);

    }

    public void removeShowAnswerStyleClasses(){
        choiceWrapper1.getStyleClass().remove("correctAnswer");
        choiceWrapper1.getStyleClass().remove("incorrectAnswer");
        choiceWrapper2.getStyleClass().remove("correctAnswer");
        choiceWrapper2.getStyleClass().remove("incorrectAnswer");
        choiceWrapper3.getStyleClass().remove("correctAnswer");
        choiceWrapper3.getStyleClass().remove("incorrectAnswer");
        choiceWrapper4.getStyleClass().remove("correctAnswer");
        choiceWrapper4.getStyleClass().remove("incorrectAnswer");
    }

    public void showAnswer(String correctAnswer, int questionIndex) {
        // Flag to track if the user's answer is correct
        boolean isUserCorrect = false;

        // Find the user's answer for this specific question
        for (UserAnswer answer : Model.getInstance().getUserAnswersList()) {
            if (answer.getQuestionIndex() == questionIndex) {
                // Check if the user's answer matches the correct answer
                if (answer.getAnswer().equals(correctAnswer)) {
                    isUserCorrect = true;

                    // Highlight the correct answer in green
                    switch (correctAnswer) {
                        case "A":
                            choiceWrapper1.getStyleClass().add("correctAnswer");
                            break;
                        case "B":
                            choiceWrapper2.getStyleClass().add("correctAnswer");
                            break;
                        case "C":
                            choiceWrapper3.getStyleClass().add("correctAnswer");
                            break;
                        case "D":
                            choiceWrapper4.getStyleClass().add("correctAnswer");
                            break;
                    }
                } else {
                    // If the user's answer is incorrect, highlight it in red
                    switch (answer.getAnswer()) {
                        case "A":
                            choiceWrapper1.getStyleClass().add("incorrectAnswer");
                            break;
                        case "B":
                            choiceWrapper2.getStyleClass().add("incorrectAnswer");
                            break;
                        case "C":
                            choiceWrapper3.getStyleClass().add("incorrectAnswer");
                            break;
                        case "D":
                            choiceWrapper4.getStyleClass().add("incorrectAnswer");
                            break;
                    }
                }
            }
        }

        // Highlight the correct answer regardless of user selection (to indicate the right choice)
        if (!isUserCorrect) {
            switch (correctAnswer) {
                case "A":
                    choiceWrapper1.getStyleClass().add("correctAnswer");
                    break;
                case "B":
                    choiceWrapper2.getStyleClass().add("correctAnswer");
                    break;
                case "C":
                    choiceWrapper3.getStyleClass().add("correctAnswer");
                    break;
                case "D":
                    choiceWrapper4.getStyleClass().add("correctAnswer");
                    break;
            }
        }
    }


}
























