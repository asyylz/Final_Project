package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.Models.*;
import javafx.concurrent.Task;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MockService implements IService {


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
                In the 16th century, an age of great marine and terrestrial exploration, Ferdinand Magellan led the first
                 expedition to sail around the world. As a young Portuguese noble, he served the king of Portugal, but 
                 he became involved in the quagmire of political intrigue at court and lost the king’s favor...
                """;

        Passage passage = new Passage("Ferdinand Magellan", text);

        Question question= new Question(
                "1. According to the passage, why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain serve the future Emperor Charles V of Spain?",
                choices,
                "A"
        );

        List<Question> listOfQuestions = new ArrayList<>();
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);
        listOfQuestions.add(question);

        return new Worksheet(
                4,
                MainSubjectOptions.ENGLISH,
                SubSubjectOptionsEnglish.COMPREHENSION,
                listOfQuestions,
                DifficultyLevelOptions.Grade3,
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

}
