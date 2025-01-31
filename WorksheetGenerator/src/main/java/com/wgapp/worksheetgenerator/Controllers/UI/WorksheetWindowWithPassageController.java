package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Utils.WorksheetPDFGenerator;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class WorksheetWindowWithPassageController implements Initializable {
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
    public Circle backgroundCircle3;
    public Circle backgroundCircle1;
    public Circle backgroundCircle2;
    public Text scoreText;
    public ImageView downloadWorksheet;
    public Circle backgroundCircle4;
    public Circle backgroundCircle5;
    public Text timerText;
    public ImageView timer;
    public HBox userAvatarWrapper;
    public Text userNameAfterLogin;
    public ImageView avatar;
    public ImageView backWindowBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fetch worksheet details
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

        // Setting  user name near avatar
        userNameAfterLogin.setText(Model.getInstance().getUserName());

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
        backWindowBtn.setOnMouseClicked(event -> {
            Stage currentStage = (Stage) backWindowBtn.getScene().getWindow();
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
            Utils.setTimer(timerText);
            isTimerOn.set(!isTimerOn.get());

        });

        avatar.setOnMouseClicked(event -> {
            BooleanProperty isLogout = Utils.notifyUser("Would you like to log out ?", "Logout request", "Logout", Alert.AlertType.CONFIRMATION);

//            if (isLogout.get()) {
//                Stage currentStage = (Stage) avatar.getScene().getWindow();
//                Model.getInstance().getViewFactory().closeStage(currentStage);
//                Model.getInstance().setUserName(null);
//            }

            if (isLogout.get()) {
                Platform.exit(); // Closes all stages and stops JavaFX
                System.exit(0);  // Ensures JVM stops completely (optional)
            }

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

        backWindowBtn.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle3.setEffect(dropShadow);
            } else if (!isShowingAnswers.get()) {
                System.out.println(isShowingAnswers.toString());
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

    private void checkTotalScore() {
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































