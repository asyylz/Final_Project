package com.wgapp.worksheetgenerator.Services.Impl;

import com.wgapp.worksheetgenerator.DAO.Entities.ChoiceEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.PassageEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.QuestionEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.WorksheetEntity;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import javafx.concurrent.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockService implements WorksheetService {
    // private static final String PROMPT_BEGINNING_COMPREHENSION;


    public MockService() {

    }

    public WorksheetEntity generateMockWorksheet() {

        // Mock data for the worksheet
        ChoiceEntity choiceEntity1 = new ChoiceEntity("A. He wanted to explore the New World");
        ChoiceEntity choiceEntity2 = new ChoiceEntity("B. He was seeking revenge on the king of Portugal");
        ChoiceEntity choiceEntity3 = new ChoiceEntity("C. He wanted to prove Spanish authority in the East Indies");
        ChoiceEntity choiceEntity4 = new ChoiceEntity("D. He wanted to gain more power and wealth");

        List<ChoiceEntity> choiceEntities = List.of(choiceEntity1, choiceEntity2, choiceEntity3, choiceEntity4);

        String text = """
                In the 16th century, an age of great marine and terrestrial exploration, Ferdinand Magellan led the first expedition to sail around the world. As a young Portuguese noble, he served the king of Portugal, but he became involved in the quagmire of political intrigue at court and lost the king’s favor. After he was dismissed from service by the king of Portugal, he offered to serve the future Emperor Charles V of Spain.
                
                A papal decree of 1493 had assigned all land in the New World west of 50 degrees W longitude to Spain and all the land east of that line to Portugal. Magellan offered to prove that the East Indies fell under Spanish authority. On September 20, 1519, Magellan set sail from Spain with five ships. More than a year later, one of these ships was exploring the topography of South America in search of a water route across the continent. This ship sank, but the remaining four ships searched along the southern peninsula of South America. Finally they found the passageEntity they sought near 50 degrees S latitude. Magellan named this passageEntity the Strait of All Saints, but today it is known as the Strait of Magellan.
                
                One ship deserted while in this passageEntity and returned to Spain, so fewer sailors were privileged to gaze at that first panorama of the Pacific Ocean. Those who remained crossed the meridian now known as the International Date Line in the early spring of 1521 after 98 days on the Pacific Ocean. During those long days at sea, many of Magellan’s men died of starvation and disease.
                
                Later, Magellan became involved in an insular conflict in the Philippines and was killed in a tribal battle. Only one ship and 17 sailors under the command of the Basque navigator Elcano survived to complete the westward journey to Spain and thus prove once and for all that the world is round, with no precipice at the edge.
                    In the 16th century, an age of great marine and terrestrial exploration, Ferdinand Magellan led the first expedition to sail around the world. As a young Portuguese noble, he served the king of Portugal, but he became involved in the quagmire of political intrigue at court and lost the king’s favor. After he was dismissed from service by the king of Portugal, he offered to serve the future Emperor Charles V of Spain.
                
                A papal decree of 1493 had assigned all land in the New World west of 50 degrees W longitude to Spain and all the land east of that line to Portugal. Magellan offered to prove that the East Indies fell under Spanish authority. On September 20, 1519, Magellan set sail from Spain with five ships. More than a year later, one of these ships was exploring the topography of South America in search of a water route across the continent. This ship sank, but the remaining four ships searched along the southern peninsula of South America. Finally they found the passageEntity they sought near 50 degrees S latitude. Magellan named this passageEntity the Strait of All Saints, but today it is known as the Strait of Magellan.
                
                One ship deserted while in this passageEntity and returned to Spain, so fewer sailors were privileged to gaze at that first panorama of the Pacific Ocean. Those who remained crossed the meridian now known as the International Date Line in the early spring of 1521 after 98 days on the Pacific Ocean. During those long days at sea, many of Magellan’s men died of starvation and disease.
                
                Later, Magellan became involved in an insular conflict in the Philippines and was killed in a tribal battle. Only one ship and 17 sailors under the command of the Basque navigator Elcano survived to complete the westward journey to Spain and thus prove once and for all that the world is round, with no precipice at the edge.
                """;

        PassageEntity passageEntity = new PassageEntity("Ferdinand Magellan", text);


        List<QuestionEntity> listOfQuestionEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            QuestionEntity q = new QuestionEntity(
                    "1. According to the passageEntity, why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain serve the future Emperor Charles V of Spain?",
                    choiceEntities,
                    "A"
            ); // Create a new Question object for each index
            listOfQuestionEntities.add(q);
        }

        return new WorksheetEntity(
                4,
                MainSubjectOptions.ENGLISH,
                SubSubjectOptionsEnglish.COMPREHENSION,
                listOfQuestionEntities,
                DifficultyLevelOptions.GRADE3,
                passageEntity
        );
    }

    @Override
    public CompletableFuture<WorksheetEntity> generateWorksheetAsync(WorksheetEntity worksheet) {
        CompletableFuture<WorksheetEntity> future = new CompletableFuture<>();

        Task<WorksheetEntity> generateWorksheetTask = new Task<>() {
            @Override
            protected WorksheetEntity call() throws Exception {
                return generateMockWorksheet();
            }
        };

        generateWorksheetTask.setOnSucceeded(event -> {
            WorksheetEntity generatedWorksheetEntity = generateWorksheetTask.getValue();
            future.complete(generatedWorksheetEntity);
        });

        generateWorksheetTask.setOnFailed(event -> {
            future.completeExceptionally(generateWorksheetTask.getException());
        });

        new Thread(generateWorksheetTask).start();

        return future;
    }

    @Override
    public CompletableFuture<WorksheetEntity> findWorksheetAsync(String searchTerm) {
        CompletableFuture<WorksheetEntity> future = new CompletableFuture<>();

        Task<WorksheetEntity> findWorksheetTask = new Task<>() {
            @Override
            protected WorksheetEntity call() throws Exception {
                return generateMockWorksheet();
            }
        };

        findWorksheetTask.setOnSucceeded(event -> {
            WorksheetEntity generatedWorksheetEntity = findWorksheetTask.getValue();
            future.complete(generatedWorksheetEntity);
        });

        findWorksheetTask.setOnFailed(event -> {
            future.completeExceptionally(findWorksheetTask.getException());
        });

        new Thread(findWorksheetTask).start();

        return future;

    }

}
