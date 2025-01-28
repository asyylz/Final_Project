package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.Models.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.Question;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

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
        //Seeting passage title
        passageTitle.setText(Model.getInstance().getWorksheet().getPassage().getPassageTitle());

        // Dynamically update wrapping width for passageText based on the width of innerLeft
        bottomLeftSection.widthProperty().addListener((observable, oldValue, newValue) -> {
            double newWidth = newValue.doubleValue() - 40; // Adjust for padding or margins
           // System.out.println("Updated Width from innerLeft widthProperty: " + newWidth);

            // Update the wrapping width of the passageText
            passageText.setWrappingWidth(newWidth);
        });

        initializeQuestions();

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
                controller.setQuestion(question, bottomRightSection);

                // Store the QuestionComponent in the list
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
