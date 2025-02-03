package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.DAO.Entities.*;
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

public class WorksheetController {
    private final MockService mockService = new MockService();
    private final WorksheetService worksheetService = new WorksheetServiceImpl();
    private WorksheetEntity worksheetEntity;
    private List<WorksheetObserver> observers = new ArrayList<>();


    public interface WorksheetObserver {
        void onWorksheetGenerated(WorksheetProperty worksheetProperty);
    }

    public void addObserver(WorksheetObserver observer) {
        observers.add(observer);
    }

    public void generateWorksheet(WorksheetProperty worksheetPropertyDTO) {
        WorksheetEntity worksheetEntity = convertFromPropertyDTOToEntity(worksheetPropertyDTO);
        mockService.generateWorksheetAsync(worksheetEntity)
                .thenAccept(worksheet -> {
                    try {
                        Platform.runLater(() -> {
                            this.worksheetEntity = worksheet;
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
                            this.worksheetEntity = worksheet;
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
            WorksheetProperty worksheetProperty = convertFromEntityToDTOProperty(this.worksheetEntity);
            observer.onWorksheetGenerated(worksheetProperty);
        }
    }


    private WorksheetProperty convertFromEntityToDTOProperty(WorksheetEntity worksheetEntity) {
        if (worksheetEntity == null) {
            throw new CustomDatabaseException("The worksheet you are looking for is not found please try  another worksheet");
        }

        // Convert list of questions (assuming worksheet.getQuestionList() is of type List<Question>)
        ListProperty<QuestionProperty> questionPropertiesList = new SimpleListProperty<>(FXCollections.observableArrayList());
        ListProperty<ChoiceProperty> choiceList = new SimpleListProperty<>(FXCollections.observableArrayList());

        for (QuestionEntity questionEntity : worksheetEntity.getQuestionList()) {
            QuestionProperty questionProperty = new QuestionProperty(
                    new SimpleIntegerProperty(questionEntity.getQuestionId()),
                    new SimpleStringProperty(questionEntity.getQuestionText()),
                    new SimpleStringProperty(questionEntity.getCorrectAnswerText()));

            for (ChoiceEntity choiceEntity : questionEntity.getChoices()) {
                choiceList.add(new ChoiceProperty(
                        new SimpleIntegerProperty(choiceEntity.getChoiceId()),
                        new SimpleStringProperty(choiceEntity.getChoiceText())));
            }
            questionProperty.setChoices(choiceList);
            questionPropertiesList.add(questionProperty);


        }

        // Check for null in passage
        PassageProperty passageProperty = null;
        if (worksheetEntity.getPassage() != null) {
            passageProperty = new PassageProperty(
                    //new SimpleIntegerProperty(worksheet.getPassage().getPassageId()),
                    new SimpleStringProperty(worksheetEntity.getPassage().getPassageTitle()),
                    new SimpleStringProperty(worksheetEntity.getPassage().getPassageText())
            );

        }

        WorksheetProperty worksheetProperty = new WorksheetProperty(
                new SimpleIntegerProperty(worksheetEntity.getWorksheetId()),
                worksheetEntity.getMainSubject(), // ✅ Directly pass enum
                worksheetEntity.getSubSubject(),  // ✅ Directly pass enum
                worksheetEntity.getDifficultyLevel(), // ✅ Directly pass enum
                new SimpleListProperty<>(FXCollections.observableArrayList(questionPropertiesList)),
                passageProperty  // May be null if passage is missing
        );

        return worksheetProperty;

    }

    private WorksheetEntity convertFromPropertyDTOToEntity(WorksheetProperty worksheetPropertyDTO) {
        if (worksheetPropertyDTO == null) {
            //throw new CustomDatabaseException("The worksheet you are looking for is not found please try  another worksheet");
        }

        System.out.println(worksheetPropertyDTO.getUserProperty().getUsername());
        WorksheetEntity worksheetEntity = new WorksheetEntity();
        worksheetEntity.setMainSubject(worksheetPropertyDTO.getMainSubject());
        worksheetEntity.setSubSubject(worksheetPropertyDTO.getSubSubject());
        worksheetEntity.setDifficultyLevel(worksheetPropertyDTO.getDiffLevel());
        worksheetEntity.setPassage(new PassageEntity(
                worksheetPropertyDTO.passageProperty().getPassageTitle(),
                worksheetPropertyDTO.passageProperty().getPassageContent()

        ));
        worksheetEntity.setQuestionTypesList(worksheetPropertyDTO.getQuestionTypeList());
        worksheetEntity.setUserEntity(new UserEntity(worksheetPropertyDTO.getUserProperty().getUsername()));

        return worksheetEntity;
    }


}
