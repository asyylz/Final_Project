package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.Models.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.Question;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorksheetWindowWithPassageController implements Initializable {
    public AnchorPane worksheetWindowWithPassageParent;
    public HBox topSection;
    public HBox bottomSection;
    public ScrollPane bottomLeftSection;
    public ScrollPane bottomRightSection;
    public AnchorPane innerLeft;
    public VBox innerRight;
    public Text subSubjectText;
    public Text mainSubjectText;
    public Text gradeLevel;
    public Text worksheetIdText;
    public Text passageText;
    public Text passageTitle;
    public Button showAnswersBtn;
    public Button closeBtn;
    public Button clearSelectionBtn;

    private final BooleanProperty isShowingAnswers = new SimpleBooleanProperty(false);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Fetch worksheet details
        int worksheetId = Model.getInstance().getWorksheet().getWorksheetId();
        MainSubjectOptions mainSubject = Model.getInstance().getWorksheet().getMainSubject();
        ISubSubjectOptions subSubject = Model.getInstance().getWorksheet().getSubSubject();
        DifficultyLevelOptions diffLevel = Model.getInstance().getWorksheet().getDifficultyLevel();

        // Setting worksheetId text
        worksheetIdText.setText("Worksheet Id: " + worksheetId);
        //Setting main subject
        mainSubjectText.setText("Main Subject: " + (mainSubject.toString().substring(0, 1)) + (mainSubject.toString().substring(1).toLowerCase()));
        // Setting sub-subject
        subSubjectText.setText("Sub Subject: " + (subSubject.toString().substring(0, 1)) + (subSubject.toString().substring(1).toLowerCase()));
        // Setting grade level
        gradeLevel.setText("Grade Level: " + diffLevel);
        //Setting passage
        passageText.setText(Model.getInstance().getWorksheet().getPassage().getPassageText());
        //Setting passage title
        passageTitle.setText(Model.getInstance().getWorksheet().getPassage().getPassageTitle());

        // Dynamically update wrapping width for passageText based on the width of innerLeft
        bottomLeftSection.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue() - 40; // Adjust for padding or margins

            bottomLeftSection.heightProperty().get();
            // Update the wrapping width of the passageText
            passageText.setWrappingWidth(newWidth);
            passageTitle.setWrappingWidth(newWidth);
        });

        // Questions being set to Ui
        initializeQuestions();
        closeBtn.setOnAction(event -> {
            Stage currentStage = (Stage) closeBtn.getScene().getWindow();
            currentStage.close();
        });

        // Clearing all userAnswers
        clearSelectionBtn.setOnAction(event -> {
            Model.getInstance().getUserAnswersList().clear();
            onUserAnswersClearedListener();
            isShowingAnswers.set(false);

        });

        showAnswersBtn.setOnAction(event -> {
            onShowAnswerBtnClickedHandler();
            isShowingAnswers.set(!isShowingAnswers.get());
        });

        isShowingAnswers.addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                showAnswersBtn.setText("Hide Answers");
            } else {
                showAnswersBtn.setText("Show Answers");
            }
        });


    }


    private void initializeQuestions() {
        List<Question> questionList = Model.getInstance().getWorksheet().getQuestionList();
        // Prepare a separate list to store QuestionComponents
        List<Node> questionComponents = new ArrayList<>();

        // For each question, load and add a QuestionComponent
        for (Question question : questionList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Fxml/QuestionComponent.fxml"));
                Node questionComponent = loader.load();

                // Get the controller and set the question data
                QuestionComponentController controller = loader.getController();
                controller.setQuestion(question, questionList.indexOf(question), bottomRightSection);

                // Store the controller in the node's properties
                questionComponent.getProperties().put("controller", controller);
                questionComponent.getProperties().put("questionIndex", questionList.indexOf(question));

                //Store the QuestionComponent in the list
                questionComponents.add(questionComponent);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error appropriately
            }
        }
        innerRight.getChildren().clear();
        innerRight.setSpacing(10);
        innerRight.getChildren().addAll(questionComponents);

    }

    public void onUserAnswersClearedListener() {
        try {
            // Iterate through all children in the VBox (userAnswers)
            for (int i = 0; i < innerRight.getChildren().size(); i++) {
                Node node = innerRight.getChildren().get(i);
                if (node.getId().equals("questionWrapper")) {
                    // Get the controller of the current question
                    QuestionComponentController controller = (QuestionComponentController) node.getProperties().get("controller");


                    // If the controller is present, call the clearSelection method
                    if (controller != null) {
                        controller.clearSelection(); // Clear the selection for the current question
                        controller.removeShowAnswerStyleClasses();
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onShowAnswerBtnClickedHandler() {

        try {
            List<Question> questions = Model.getInstance().getWorksheet().getQuestionList();
            // Iterate through all children in the VBox (userAnswers)
            for (int i = 0; i < innerRight.getChildren().size(); i++) {
                Node node = innerRight.getChildren().get(i);
                if (node.getId().equals("questionWrapper")) {
                    // Get the controller of the current question
                    QuestionComponentController controller = (QuestionComponentController) node.getProperties().get("controller");
                    int nodeIndex = (int) node.getProperties().get("questionIndex");

                    // If the controller is present, call the showAnswers method
                    if (controller != null) {
                        for (Question question : questions) {
                            if ((questions.indexOf(question) == nodeIndex) && !isShowingAnswers.get()) {
                                controller.showAnswer(question.getCorrectAnswerText(), questions.indexOf(question));
                            } else if (isShowingAnswers.get()) {
                                controller.removeShowAnswerStyleClasses();
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}































