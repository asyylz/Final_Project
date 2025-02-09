package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.WorksheetController;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Utils.WorksheetPDFGenerator;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class WorksheetWindowWithPassageController implements Initializable, WorksheetController.WorksheetObserver {
    public AnchorPane worksheetWindowWithPassageParent;
    public HBox bottomSection;
    public VBox bottomLeftSection;
    public ScrollPane bottomRightSection;
    public AnchorPane innerLeft;
    public VBox innerRight;
    public Text subSubjectText;
    public Text mainSubjectText;
    public Text gradeLevel;
    public Text worksheetIdText;
    public TextArea passageText;
    public Text passageTitle;
    public ImageView clearSelectionBtn;
    public ImageView showAnswersBtn;
    private final BooleanProperty isShowingAnswers = new SimpleBooleanProperty(false);
    private final BooleanProperty isTimerOn = new SimpleBooleanProperty(false);
    private final BooleanProperty isItAtStart = new SimpleBooleanProperty(true);
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
    public ImageView deleteWorksheet;
    public Circle backgroundCircle7;
    private final WorksheetController worksheetController = new WorksheetController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Observer pattern
        worksheetController.addObserver(this);


        if (isItAtStart.get()) {
            if (Model.getInstance().getWorksheetProperty().getId() != 0) { // equals zero means null
                updateWorksheetUI();
            }
            isItAtStart.set(false);
        }

        Model.getInstance().worksheetProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == null) {
                setfieldsDefault();
            } else {
                updateWorksheetUI();
            }
        });


        searchIconBtn.setOnMouseClicked(event -> {
            worksheetController.findWorksheet(searchTextField.getText());
            searchTextField.clear();
            // To be able to load second  next search
            Model.getInstance().setWorksheetProperty(null);

        });

        searchTextField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                worksheetController.findWorksheet(searchTextField.getText());
                searchTextField.clear();
                // To be able to load second  next search
                Model.getInstance().setWorksheetProperty(null);
            }

        });

        // Dynamically update wrapping width for passageText based on the width of innerLeft
        bottomLeftSection.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue() - 40; // Adjust for padding or margins

            bottomLeftSection.heightProperty().get();
            // Update the wrapping width of the passageText
            //passageText.setWrappingWidth(newWidth);
            passageTitle.setWrappingWidth(newWidth);
        });


        exitBtn.setOnMouseClicked(event -> {
            Stage currentStage = (Stage) exitBtn.getScene().getWindow();
            currentStage.close();
        });


        // Clearing all userAnswers
        clearSelectionBtn.setOnMouseClicked(event -> {
            Model.getInstance().getWorksheetProperty().getUserAnswerPropertyList().clear();
            onUserAnswersClearedListener();
            isShowingAnswers.set(false);

        });

        showAnswersBtn.setOnMouseClicked(event -> {
            onShowAnswerBtnClickedHandler();


        });

        downloadWorksheet.setOnMouseClicked(event -> {
            WorksheetPDFGenerator.downloadWorksheetHandler(downloadWorksheet.getScene().getWindow());
        });

        deleteWorksheet.setOnMouseClicked(event -> {
            Utils.notifyUser("Are you sure to delete this worksheet?", "Delete", "Warning", Alert.AlertType.CONFIRMATION);
            Model.getInstance().getWorksheetProperty().setUserProperty(Model.getInstance().getUserProperty());
            worksheetController.deleteWorksheet(Model.getInstance().getWorksheetProperty());

        });




        timer.setOnMouseClicked(event -> {
            if (!isTimerOn.get()) {
                Utils.setTimer(timerText);  // Start the timer
            } else {
                Utils.stopTimer();
                //timerText.setText("");      // Clear the timer when stopping
            }
            isTimerOn.set(!isTimerOn.get()); // Toggle the state
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

        deleteWorksheet.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                backgroundCircle7.setEffect(dropShadow);
            } else {
                backgroundCircle7.setEffect(null);
            }
        });

        /*======================================== END OF DROP DOWN SHADOW EFFECT =============================================*/
    }   /*======================================== END OF INITIALIZER =============================================*/


    // Extracted method to update UI whenever a worksheet is found
    private void updateWorksheetUI() {
        int worksheetId = Model.getInstance().getWorksheetProperty().getId();
        MainSubjectOptions mainSubject = Model.getInstance().getWorksheetProperty().getMainSubject();
        ISubSubjectOptions subSubject = Model.getInstance().getWorksheetProperty().getSubSubject();
        DifficultyLevelOptions diffLevel = Model.getInstance().getWorksheetProperty().getDiffLevel();

        // Setting worksheetId text
        worksheetIdText.setText(String.valueOf(worksheetId));
        //Setting main subject
        mainSubjectText.setText((mainSubject.toString().substring(0, 1)) + (mainSubject.toString().substring(1)));
        // Setting sub-subject
        subSubjectText.setText((subSubject.toString().substring(0, 1)) + (subSubject.toString().substring(1)));
        // Setting grade level
        gradeLevel.setText(diffLevel.toString());

        //Setting passage
        if (Model.getInstance().getWorksheetProperty().passageProperty() != null) {
            passageText.setText(Model.getInstance().getWorksheetProperty().passageProperty().getPassageContent());
            //Setting passage title
            passageTitle.setText(Model.getInstance().getWorksheetProperty().passageProperty().getPassageTitle());
        } else {
            passageText.setText("");
            passageTitle.setText("");
        }


        // At first score text invisible
        scoreText.setText("");

        // Questions being set to Ui
        initializeQuestions();

    }

    private void checkTotalScore() {
        if (!isShowingAnswers.get()) {
            int totalScore = 0;
            int numberOfQuestion = Model.getInstance().getWorksheetProperty().getQuestionList().size();
            List<QuestionProperty> questionList = Model.getInstance().getWorksheetProperty().getQuestionList();
            List<UserAnswerProperty> userAnswers = Model.getInstance().getWorksheetProperty().getUserAnswerPropertyList();

            for (UserAnswerProperty userAnswer : userAnswers) {
                for (QuestionProperty question : questionList)
                    if (questionList.indexOf(question) == userAnswer.getQuestionIndex() && userAnswer.getAnswer().equals(question.getCorrectAnswer())) {
                        totalScore++;
                    }
            }

            scoreText.setText("Total Score: " + totalScore + "/" + numberOfQuestion);
        } else {
            scoreText.setText("");
        }
    }

    private void initializeQuestions() {
        List<QuestionProperty> questionList = Model.getInstance().getWorksheetProperty().getQuestionList();
        // Prepare a separate list to store QuestionComponents
        List<Node> questionComponents = new ArrayList<>();

        if (questionList != null && questionList.size() > 0) {
            // For each question, load and add a QuestionComponent
            for (QuestionProperty question : questionList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/QuestionComponent.fxml"));
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

        if (!isShowingAnswers.get()) {
            BooleanProperty isPinCorrect = Utils.notifyUser("Please enter pin number", "", "UNLOCK", Alert.AlertType.CONFIRMATION, new SimpleBooleanProperty(true));

            if (isPinCorrect.get()) {

                isShowingAnswers.set(!isShowingAnswers.get());

                try {
                    List<QuestionProperty> questions = Model.getInstance().getWorksheetProperty().getQuestionList();
                    // Iterate through all children in the VBox (userAnswers)
                    for (int i = 0; i < innerRight.getChildren().size(); i++) {
                        Node node = innerRight.getChildren().get(i);
                        if (node.getId().equals("questionWrapper")) {
                            // Get the controller of the current question
                            QuestionComponentController controller = (QuestionComponentController) node.getProperties().get("controller");
                            int nodeIndex = (int) node.getProperties().get("questionIndex");

                            // If the controller is present, call the showAnswers method
                            if (controller != null) {
                                for (QuestionProperty question : questions) {
                                    if (questions.indexOf(question) == nodeIndex) {
                                        controller.showAnswer(question.getCorrectAnswer(), questions.indexOf(question));
                                    }
                                }
                            }
                        }

                    }
                    checkTotalScore();

                } catch (Exception e) {
                    e.printStackTrace();
                    if (e.getMessage().contains("Cannot invoke \"String.hashCode()\" because \"<local4>\" is null")) {
                        Utils.notifyUser("Answers are not available yet please try later", "Showing Answers", "ANSWERS", Alert.AlertType.WARNING);
                        isShowingAnswers.set(false);
                    }

                }
            }

        } else {


            try {
                List<QuestionProperty> questions = Model.getInstance().getWorksheetProperty().getQuestionList();
                // Iterate through all children in the VBox (userAnswers)
                for (int i = 0; i < innerRight.getChildren().size(); i++) {
                    Node node = innerRight.getChildren().get(i);
                    if (node.getId().equals("questionWrapper")) {
                        // Get the controller of the current question
                        QuestionComponentController controller = (QuestionComponentController) node.getProperties().get("controller");
                        int nodeIndex = (int) node.getProperties().get("questionIndex");

                        // If the controller is present, call the showAnswers method
                        if (controller != null) {
                            for (QuestionProperty question : questions) {
                                if ((questions.indexOf(question) == nodeIndex)) {
                                    controller.removeShowAnswerStyleClasses();
                                }
                            }
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
                if (e.getMessage().contains("Cannot invoke \"String.hashCode()\" because \"<local4>\" is null")) {
                    Utils.notifyUser("Answers are not available yet please try later", "Showing Answers", "ANSWERS", Alert.AlertType.WARNING);
                }

            }
            isShowingAnswers.set(!isShowingAnswers.get());
        }
    }

    private void setfieldsDefault() {
        // Setting worksheetId text
        worksheetIdText.setText("");
        //Setting main subject
        mainSubjectText.setText("");
        // Setting sub-subject
        subSubjectText.setText("");
        // Setting grade level
        gradeLevel.setText("");
        //Setting passage
        passageText.setText("");
        //Setting passage title
        passageTitle.setText("");

        // At first score text invisible
        scoreText.setText("");

        innerRight.getChildren().clear();
    }

    @Override
// Since generation happens in generation view this observer does only notificataion
    public void onWorksheetGenerated(WorksheetProperty worksheetProperty) {
        Utils.notifyUser("Worksheet has been found successfully!", "Worksheet Generated", "Success", Alert.AlertType.INFORMATION);
        updateWorksheetUI();
    }

    @Override
    public void onWorksheetDeleted() {
        Utils.notifyUser("Worksheet deleted successfully.", "Delete", "Success", Alert.AlertType.INFORMATION);
        for(WorksheetProperty wp:Model.getInstance().getWorksheetPropertyList()){
            if(wp.getId() ==Model.getInstance().getWorksheetProperty().getId()) {
                Model.getInstance().getWorksheetPropertyList().remove(wp); // Deletion should be reflected to history list

            }
        }
        Model.getInstance().setWorksheetProperty(null);
        updateWorksheetUI();
    }

    @Override
    public void onWorksheetFound(WorksheetProperty worksheetProperty) {
        Model.getInstance().setWorksheetProperty(worksheetProperty);
        Model.getInstance().getWorksheetProperty().setUserProperty(Model.getInstance().getUserProperty());
        Utils.notifyUser("Worksheet has been found successfully!", "Worksheet Found", "Success", Alert.AlertType.INFORMATION);
        updateWorksheetUI();
    }

    @Override
    public void onWorksheetsListed(ListProperty<WorksheetProperty> worksheetPropertyList) {

    }

}
























