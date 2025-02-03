package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Exceptions.CustomDatabaseException;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.Services.Impl.MockService;
import com.wgapp.worksheetgenerator.Services.Impl.WorksheetServiceImpl;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import java.util.ArrayList;
import java.util.List;

public class WorksheetControllerTest {
    private final MockService mockService = new MockService();
    private final WorksheetService worksheetService = new WorksheetServiceImpl();
    //private final WorksheetServiceImpl worksheetServiceImpl = new WorksheetServiceImpl();
    private Worksheet worksheet;
    private  WorksheetProperty worksheetProperty = new WorksheetProperty();
    private List<WorksheetObserver> observers = new ArrayList<>();


    public interface WorksheetObserver {
        void onWorksheetGenerated(WorksheetProperty worksheetProperty);
    }

    public void addObserver(WorksheetObserver observer) {
        observers.add(observer);
    }

    public void generateWorksheet() {
        mockService.generateWorksheetAsync()
                .thenAccept(worksheet -> {
                    try {
                        Platform.runLater(() -> {
                            this.worksheet = worksheet;
                            notifyObservers();
                        });
                    } catch (Exception e) {
                        System.err.println("Error updating worksheet: " + e.getMessage());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Error generating worksheet: " + ex.getMessage());
                    return null;  // Returning null to handle failure
                });
    }

    public void findWorksheet(String searchTerm) {
        worksheetService.findWorksheetAsync(searchTerm)
                .thenAccept(worksheet -> {
                    try {
                        Platform.runLater(() -> {
                            this.worksheet = worksheet;
                            notifyObservers();
                        });

                    } catch (Exception e) {
                        System.err.println("Error fetching worksheet: " + e.getMessage());
                    }
                }).exceptionally(ex -> {
                    return null;
                });
    }

    private void notifyObservers() {
        for (WorksheetObserver observer : observers) {

            WorksheetProperty worksheetProperty = convertorWorksheetFromDTOtoProperty(this.worksheet);
            observer.onWorksheetGenerated(worksheetProperty);
        }
    }


    private WorksheetProperty convertorWorksheetFromDTOtoProperty(Worksheet worksheet) {

        if (worksheet == null) {
            throw new CustomDatabaseException("The worksheet you are looking for is not found please try  another worksheet");
        }

        // Convert list of questions (assuming worksheet.getQuestionList() is of type List<Question>)
        ListProperty<QuestionProperty> questionPropertiesList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListProperty<ChoiceProperty> choiceList = new SimpleListProperty<>(FXCollections.observableArrayList());

        for (Question question : worksheet.getQuestionList()) {
            QuestionProperty questionProperty = new QuestionProperty(
                    new SimpleIntegerProperty(question.getQuestionId()),
                    new SimpleStringProperty(question.getQuestionText()),
                    new SimpleStringProperty(question.getCorrectAnswerText()));  // You might need to create a constructor or method for this

            for (Choice choice : question.getChoices()) {
                choiceList.add(new ChoiceProperty(
                        new SimpleIntegerProperty(choice.getChoiceId()),
                        new SimpleStringProperty(choice.getChoiceText())));
            }
            questionProperty.setChoices(choiceList);
            questionPropertiesList.add(questionProperty);


        }

        // Check for null in passage
        PassageProperty passageProperty = null;
        if (worksheet.getPassage() != null) {
            passageProperty = new PassageProperty(
                    //new SimpleIntegerProperty(worksheet.getPassage().getPassageId()),
                    new SimpleStringProperty(worksheet.getPassage().getPassageTitle()),
                    new SimpleStringProperty(worksheet.getPassage().getPassageText())
                    );

        }

        WorksheetProperty worksheetProperty = new WorksheetProperty(
                new SimpleIntegerProperty(worksheet.getWorksheetId()),
                worksheet.getMainSubject(), // ✅ Directly pass enum
                worksheet.getSubSubject(),  // ✅ Directly pass enum
                worksheet.getDifficultyLevel(), // ✅ Directly pass enum
                new SimpleListProperty<>(FXCollections.observableArrayList(questionPropertiesList)),
                passageProperty  // May be null if passage is missing
        );

        return worksheetProperty;

    }

}