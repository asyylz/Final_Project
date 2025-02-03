package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.Question;
import com.wgapp.worksheetgenerator.ModelsUI.QuestionProperty;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class WorksheetWindowController implements Initializable {
    public AnchorPane worksheetWindowParent;
    public Text worksheetIdText;
    public VBox questionWrapperParent;
    public Text subSubjectText;
    public Text mainSubjectText;
    public Text gradeLevel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Fetch worksheet details
        int worksheetId = Model.getInstance().getWorksheetProperty().getId();;
        MainSubjectOptions mainSubject = Model.getInstance().getWorksheetProperty().getMainSubject();
        ISubSubjectOptions subSubject = Model.getInstance().getWorksheetProperty().getSubSubject();
        DifficultyLevelOptions diffLevel = Model.getInstance().getWorksheetProperty().getDiffLevel();
        List<QuestionProperty> questionList = Model.getInstance().getWorksheetProperty().getQuestionList();

        // Setting worksheetId text
        worksheetIdText.setText("Worksheet Id: " + worksheetId);
        //Setting main subject
        mainSubjectText.setText("Main Subject: " + mainSubject);
        // Setting sub-subject
        subSubjectText.setText("Sub Subject: " + subSubject);
        // Setting grade level
        gradeLevel.setText("Grade Level: " + diffLevel);


        // Prepare a separate list to store QuestionComponents
        List<Node> questionComponents = new ArrayList<>();

        // For each question, load and add a QuestionComponent
        for (QuestionProperty question : questionList) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/QuestionComponent.fxml"));
                Node questionComponent = loader.load();

                // Get the controller and set the question data
                QuestionComponentController controller = loader.getController();
                //controller.setQuestion(question);

                // Store the QuestionComponent in the list
                questionComponents.add(questionComponent);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the error appropriately
            }
        }

        // Set up pagination
        int questionsPerPage = 4; // Questions per page
        Pagination pagination = new Pagination(
                (questionComponents.size() + questionsPerPage - 1) / questionsPerPage, // Calculate total pages
                0 // Start at page 0
        );

        pagination.setPageFactory(pageIndex -> {
            VBox page = new VBox(10); // VBox for the current page, with spacing of 10 between questions
            int start = pageIndex * questionsPerPage;
            int end = Math.min(start + questionsPerPage, questionComponents.size());

            // Add only the questions for the current page
            for (int i = start; i < end; i++) {
                page.getChildren().add(questionComponents.get(i));
            }

            return page;
        });

        // Add pagination to your layout
        worksheetWindowParent.getChildren().add(pagination);
        AnchorPane.setTopAnchor(pagination, 100.0);  // Adjust as needed
        AnchorPane.setLeftAnchor(pagination, 0.0);
        AnchorPane.setRightAnchor(pagination, 0.0);
    }

}
