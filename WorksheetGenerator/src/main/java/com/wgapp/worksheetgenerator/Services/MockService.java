package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.Models.*;
import javafx.concurrent.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockService implements IService {
    // private static final String PROMPT_BEGINNING_COMPREHENSION;


    public MockService() {

    }

    public Worksheet generateMockWorksheet() {

        // Mock data for the worksheet
        Choice choice1 = new Choice("A. He wanted to explore the New World");
        Choice choice2 = new Choice("B. He was seeking revenge on the king of Portugal");
        Choice choice3 = new Choice("C. He wanted to prove Spanish authority in the East Indies");
        Choice choice4 = new Choice("D. He wanted to gain more power and wealth");

        List<Choice> choices = List.of(choice1, choice2, choice3, choice4);

        String text = """
                In the 16th century, an age of great marine and terrestrial exploration, Ferdinand Magellan led the first expedition to sail around the world. As a young Portuguese noble, he served the king of Portugal, but he became involved in the quagmire of political intrigue at court and lost the king’s favor. After he was dismissed from service by the king of Portugal, he offered to serve the future Emperor Charles V of Spain.
                
                A papal decree of 1493 had assigned all land in the New World west of 50 degrees W longitude to Spain and all the land east of that line to Portugal. Magellan offered to prove that the East Indies fell under Spanish authority. On September 20, 1519, Magellan set sail from Spain with five ships. More than a year later, one of these ships was exploring the topography of South America in search of a water route across the continent. This ship sank, but the remaining four ships searched along the southern peninsula of South America. Finally they found the passage they sought near 50 degrees S latitude. Magellan named this passage the Strait of All Saints, but today it is known as the Strait of Magellan.
                
                One ship deserted while in this passage and returned to Spain, so fewer sailors were privileged to gaze at that first panorama of the Pacific Ocean. Those who remained crossed the meridian now known as the International Date Line in the early spring of 1521 after 98 days on the Pacific Ocean. During those long days at sea, many of Magellan’s men died of starvation and disease.
                
                Later, Magellan became involved in an insular conflict in the Philippines and was killed in a tribal battle. Only one ship and 17 sailors under the command of the Basque navigator Elcano survived to complete the westward journey to Spain and thus prove once and for all that the world is round, with no precipice at the edge.
                    In the 16th century, an age of great marine and terrestrial exploration, Ferdinand Magellan led the first expedition to sail around the world. As a young Portuguese noble, he served the king of Portugal, but he became involved in the quagmire of political intrigue at court and lost the king’s favor. After he was dismissed from service by the king of Portugal, he offered to serve the future Emperor Charles V of Spain.
                
                A papal decree of 1493 had assigned all land in the New World west of 50 degrees W longitude to Spain and all the land east of that line to Portugal. Magellan offered to prove that the East Indies fell under Spanish authority. On September 20, 1519, Magellan set sail from Spain with five ships. More than a year later, one of these ships was exploring the topography of South America in search of a water route across the continent. This ship sank, but the remaining four ships searched along the southern peninsula of South America. Finally they found the passage they sought near 50 degrees S latitude. Magellan named this passage the Strait of All Saints, but today it is known as the Strait of Magellan.
                
                One ship deserted while in this passage and returned to Spain, so fewer sailors were privileged to gaze at that first panorama of the Pacific Ocean. Those who remained crossed the meridian now known as the International Date Line in the early spring of 1521 after 98 days on the Pacific Ocean. During those long days at sea, many of Magellan’s men died of starvation and disease.
                
                Later, Magellan became involved in an insular conflict in the Philippines and was killed in a tribal battle. Only one ship and 17 sailors under the command of the Basque navigator Elcano survived to complete the westward journey to Spain and thus prove once and for all that the world is round, with no precipice at the edge.
                """;

        Passage passage = new Passage("Ferdinand Magellan", text);


        List<Question> listOfQuestions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Question q = new Question(
                    "1. According to the passage, why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain serve the future Emperor Charles V of Spain?",
                    choices,
                    "A"
            ); // Create a new Question object for each index
            listOfQuestions.add(q);
        }

        return new Worksheet(
                4,
                MainSubjectOptions.ENGLISH,
                SubSubjectOptionsEnglish.COMPREHENSION,
                listOfQuestions,
                DifficultyLevelOptions.GRADE3,
                passage
        );
    }

    @Override
    public CompletableFuture<Worksheet> generateWorksheetAsync() {
        CompletableFuture<Worksheet> future = new CompletableFuture<>();

        Task<Worksheet> generateWorksheetTask = new Task<>() {
            @Override
            protected Worksheet call() throws Exception {
                return generateMockWorksheet();
            }
        };

        generateWorksheetTask.setOnSucceeded(event -> {
            Worksheet generatedWorksheet = generateWorksheetTask.getValue();
            future.complete(generatedWorksheet);
        });

        generateWorksheetTask.setOnFailed(event -> {
            future.completeExceptionally(generateWorksheetTask.getException());
        });

        new Thread(generateWorksheetTask).start();

        return future;
    }

    @Override
    public CompletableFuture<Worksheet> findWorksheetAsync(String searchTerm) {
        CompletableFuture<Worksheet> future = new CompletableFuture<>();

        Task<Worksheet> findWorksheetTask = new Task<>() {
            @Override
            protected Worksheet call() throws Exception {
                return generateMockWorksheet();
            }
        };

        findWorksheetTask.setOnSucceeded(event -> {
            Worksheet generatedWorksheet = findWorksheetTask.getValue();
            future.complete(generatedWorksheet);
        });

        findWorksheetTask.setOnFailed(event -> {
            future.completeExceptionally(findWorksheetTask.getException());
        });

        new Thread(findWorksheetTask).start();

        return future;

    }

}
