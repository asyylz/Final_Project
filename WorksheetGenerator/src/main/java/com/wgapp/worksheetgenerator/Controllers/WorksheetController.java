package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.DAO.Entities.*;
import com.wgapp.worksheetgenerator.Exceptions.CustomDatabaseException;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.Services.Impl.MockService;
import com.wgapp.worksheetgenerator.Services.Impl.WorksheetServiceImpl;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import com.wgapp.worksheetgenerator.ViewFactory.UserMenuOptions;
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

        void onWorksheetDeleted();

        void onWorksheetUpdated(WorksheetProperty worksheetProperty);
    }

    public void addObserver(WorksheetObserver observer) {
        observers.add(observer);
    }

    public void generateWorksheet(WorksheetProperty worksheetPropertyDTO) {
        WorksheetEntity worksheetEntity = convertFromPropertyDTOToEntity(worksheetPropertyDTO);
        mockService.generateWorksheetAsync(worksheetEntity)
                .thenAccept(worksheet -> {
                    Platform.runLater(() -> {
                        this.worksheetEntity = worksheet;
                        notifyObservers("generated");  // Notify only "generated"
                        // Notify UI controller to switch to the worksheet view

                    });
                })
                .exceptionally(ex -> {
                    System.err.println("Error generating worksheet: " + ex.getMessage());
                    return null;
                });
    }

    public void findWorksheet(String searchTerm) {
        worksheetService.findWorksheetAsync(searchTerm)
                .thenAccept(worksheet -> {
                    Platform.runLater(() -> {
                        this.worksheetEntity = worksheet;
                        notifyObservers("updated");  // Notify only "updated"
                    });
                }).exceptionally(ex -> {
                    return null;
                });
    }


    public void deleteWorksheet(WorksheetProperty worksheetPropertyDTO) {
        worksheetService.deleteWorksheetAsync(
                worksheetPropertyDTO.getId(),
                worksheetPropertyDTO.getUserProperty().getUserId()
        ).thenRun(() -> {
            Platform.runLater(() -> {
                this.worksheetEntity = null; // Clear the entity after deletion
                notifyObservers("deleted");  // Notify only "deleted"
            });
        }).exceptionally(ex -> {
            System.err.println("Error deleting worksheet: " + ex.getMessage());
            return null;
        });
    }


    //================================================== NOTIFIER ====================================================//
    private void notifyObservers(String action) {
        WorksheetProperty worksheetProperty;

        for (WorksheetObserver observer : observers) {
            switch (action) {
                case "generated":
                    worksheetProperty = convertFromEntityToDTOProperty(this.worksheetEntity);
                    observer.onWorksheetGenerated(worksheetProperty);
                    break;
                case "deleted":
                    observer.onWorksheetDeleted();
                    break;
                case "updated":
                    worksheetProperty  = convertFromEntityToDTOProperty(this.worksheetEntity);
                    observer.onWorksheetUpdated(worksheetProperty);
                    break;
            }
        }
    }

    //================================================== CONVERSION ====================================================//
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
