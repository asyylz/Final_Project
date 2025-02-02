package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.WorksheetControllerTest;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Utils.WorksheetPDFGenerator;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.beans.binding.Bindings;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class WorksheetWindowWithPassageController implements Initializable, WorksheetControllerTest.WorksheetObserver {
    public AnchorPane worksheetWindowWithPassageParent;
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
    public ImageView clearSelectionBtn;
    public ImageView showAnswersBtn;
    private final BooleanProperty isShowingAnswers = new SimpleBooleanProperty(false);
    private final BooleanProperty isTimerOn = new SimpleBooleanProperty(false);
    private final BooleanProperty didWorksheetFound = new SimpleBooleanProperty(false);
    public Circle backgroundCircle3;
    public Circle backgroundCircle1;
    public Circle backgroundCircle2;
    public Text scoreText;
    public ImageView downloadWorksheet;
    public Circle backgroundCircle4;
    public Circle backgroundCircle5;
    public Text timerText;
    public ImageView timer;
    public TextField searchTextField;
    public ImageView searchIconBtn;
    public Circle backgroundCircle6;
    public ImageView exitBtn;
    private final WorksheetControllerTest worksheetController = new WorksheetControllerTest();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Observer pattern
        worksheetController.addObserver(this);

        // Bind the property to update UI automatically when `didWorksheetFound` changes

        didWorksheetFound.bind(
                Bindings.createBooleanBinding(() ->
                                Model.getInstance().getWorksheet() != null,
                        Model.getInstance().worksheetProperty()
                ));

        // Add a listener to react whenever `didWorksheetFound` changes
        didWorksheetFound.addListener((obs, oldValue, newValue) -> {
            if (newValue) {
                System.out.println("worksheet from database");
                updateWorksheetUI();
            }
        });

        // Check if a worksheet is already available at initialization
        if (Model.getInstance().getWorksheet() != null) {
            System.out.println("new worksheet generation");
//            Platform.runLater(() -> {
//               updateWorksheetUI();
//          });

            updateWorksheetUI();
        }

        searchIconBtn.setOnMouseClicked(event -> {
            //  System.out.println("Search term" + searchTextField.getText());
            worksheetController.findWorksheet(searchTextField.getText());
            searchTextField.clear();
            // To be able to load second  next search
            Model.getInstance().setWorksheet(null);

        });

        searchTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                //   System.out.println("Search term" + searchTextField.getText());
                worksheetController.findWorksheet(searchTextField.getText());
                searchTextField.clear();
                // To be able to load second  next search
                Model.getInstance().setWorksheet(null);
            }

        });

        // Dynamically update wrapping width for passageText based on the width of innerLeft
        bottomLeftSection.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue() - 40; // Adjust for padding or margins

            bottomLeftSection.heightProperty().get();
            // Update the wrapping width of the passageText
            passageText.setWrappingWidth(newWidth);
            passageTitle.setWrappingWidth(newWidth);
        });


        exitBtn.setOnMouseClicked(event -> {
            Stage currentStage = (Stage) exitBtn.getScene().getWindow();
            currentStage.close();
        });


        // Clearing all userAnswers
        clearSelectionBtn.setOnMouseClicked(event -> {
            Model.getInstance().getUserAnswersList().clear();
            onUserAnswersClearedListener();
            isShowingAnswers.set(false);

        });

        showAnswersBtn.setOnMouseClicked(event -> {
            onShowAnswerBtnClickedHandler();
            checkTotalScore();
            isShowingAnswers.set(!isShowingAnswers.get());
        });

        downloadWorksheet.setOnMouseClicked(event -> {
            WorksheetPDFGenerator.downloadWorksheetHandler(downloadWorksheet.getScene().getWindow());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Worksheet PDF Generator");
            alert.setContentText("Worksheet has been saved as PDF successfully.!");
            alert.show();

            // Create a PauseTransition to wait for 5 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(2));

            // Set an action to close the alert when the time is up
            pause.setOnFinished(e -> alert.close());

            // Start the pause transition
            pause.play();
        });

        timer.setOnMouseClicked(event -> {
            // System.out.println("Before toggle: " + isTimerOn.get());

            if (!isTimerOn.get()) {
                Utils.setTimer(timerText);  // Start the timer
            } else {
                Utils.stopTimer();
                //timerText.setText("");      // Clear the timer when stopping
            }
            isTimerOn.set(!isTimerOn.get()); // Toggle the state

            //System.out.println("After toggle: " + isTimerOn.get());
        });


        /*======================================== DROP DOWN SHADOW EFFECT =============================================*/
        DropShadow dropShadow = new DropShadow();
        dropShadow.setBlurType(BlurType.GAUSSIAN);
        dropShadow.setColor(Color.valueOf("#FFF5FA"));
        dropShadow.setRadius(50);


        showAnswersBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue || isShowingAnswers.get()) {
                backgroundCircle1.setEffect(dropShadow); // Apply shadow on hover OR when answers are shown
            } else {
                backgroundCircle1.setEffect(null); // Remove shadow when not hovering and answers are not shown
            }
        });

        clearSelectionBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle2.setEffect(dropShadow);
            } else {
                backgroundCircle2.setEffect(null);
            }
        });

        exitBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle3.setEffect(dropShadow);
            } else if (!isShowingAnswers.get()) {
                backgroundCircle3.setEffect(null);
            }
        });
        downloadWorksheet.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle4.setEffect(dropShadow);
            } else {
                backgroundCircle4.setEffect(null);
            }
        });
        searchIconBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle6.setEffect(dropShadow);
            } else {
                backgroundCircle6.setEffect(null);
            }
        });


        isShowingAnswers.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                backgroundCircle1.setEffect(dropShadow); // Keep shadow when showing answers
            } else if (!showAnswersBtn.isHover()) {
                backgroundCircle1.setEffect(null); // Remove only if not hovering
            }
        });

        isTimerOn.addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                backgroundCircle5.setEffect(dropShadow);
            } else if (!timer.isHover()) {
                backgroundCircle5.setEffect(null);
            }
        });

        timer.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue || isTimerOn.get()) {
                backgroundCircle5.setEffect(dropShadow);
            } else {
                backgroundCircle5.setEffect(null);
            }
        });


        /*======================================== END OF DROP DOWN SHADOW EFFECT =============================================*/
    }   /*======================================== END OF INITIALIZER =============================================*/


    // Extracted method to update UI whenever a worksheet is found
    private void updateWorksheetUI() {
        int worksheetId = Model.getInstance().getWorksheet().getWorksheetId();
        MainSubjectOptions mainSubject = Model.getInstance().getWorksheet().getMainSubject();
        ISubSubjectOptions subSubject = Model.getInstance().getWorksheet().getSubSubject();
        DifficultyLevelOptions diffLevel = Model.getInstance().getWorksheet().getDifficultyLevel();


        // Setting worksheetId text
        worksheetIdText.setText(String.valueOf(worksheetId));
        //Setting main subject
        mainSubjectText.setText((mainSubject.toString().substring(0, 1)) + (mainSubject.toString().substring(1)));
        // Setting sub-subject
        subSubjectText.setText((subSubject.toString().substring(0, 1)) + (subSubject.toString().substring(1)));
        // Setting grade level
        gradeLevel.setText(diffLevel.toString());
        //Setting passage
        passageText.setText(Model.getInstance().getWorksheet().getPassage().getPassageText());
        //Setting passage title
        passageTitle.setText(Model.getInstance().getWorksheet().getPassage().getPassageTitle());

        // At first score text invisible
        scoreText.setText("");

        // Questions being set to Ui
        initializeQuestions();

    }

    private void checkTotalScore() {
        if (!isShowingAnswers.get()) {
            int totalScore = 0;
            int numberOfQuestion = Model.getInstance().getWorksheet().getQuestionList().size();
            List<Question> questionList = Model.getInstance().getWorksheet().getQuestionList();
            List<UserAnswer> userAnswers = Model.getInstance().getUserAnswersList();

            for (UserAnswer userAnswer : userAnswers) {
                for (Question question : questionList)
                    if (questionList.indexOf(question) == userAnswer.getQuestionIndex() && userAnswer.getAnswer().equals(question.getCorrectAnswerText())) {
                        totalScore++;
                    }
            }

            scoreText.setText("Total Score: " + totalScore + "/" + numberOfQuestion);
        } else {
            scoreText.setText("");
        }
    }

    private void initializeQuestions() {
        List<Question> questionList = Model.getInstance().getWorksheet().getQuestionList();
        // Prepare a separate list to store QuestionComponents
        List<Node> questionComponents = new ArrayList<>();

        if (questionList != null && questionList.size() > 0) {
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


    @Override
    public void onWorksheetGenerated(Worksheet worksheet) {
        // Set new worksheet to model
       Model.getInstance().setWorksheet(worksheet);

        // Show success message
        Utils.notifyUser("Worksheet has been found successfully!", "Worksheet Generated", "Success", Alert.AlertType.INFORMATION);


    }

}































