package com.wgapp.worksheetgenerator.Services.Impl;

import com.wgapp.worksheetgenerator.Config.OpenAIConfig;
import com.wgapp.worksheetgenerator.DAO.Entities.*;
import com.wgapp.worksheetgenerator.DAO.Impl.WorksheetDAOImpl;
import com.wgapp.worksheetgenerator.ModelsUI.*;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class WorksheetServiceImpl implements WorksheetService {
    private static final OpenAIConfig OPENAI_CONFIG = new OpenAIConfig();
    private static final WorksheetDAOImpl worksheetDAO = new WorksheetDAOImpl();


    public WorksheetServiceImpl() {
    }

    public WorksheetEntity generateWorksheetCallFromController(WorksheetEntity worksheetEntity) {
        // We are  starting to build our prompt  with constant related to Model
        String initialPrompt = Utils.checkSubSubject();

        // Build the prompt starting from initial prompt
        StringBuilder promptBuilder = new StringBuilder(initialPrompt);
        promptBuilder
                .append("\nMain Subject: ").append(worksheetEntity.getMainSubject())
                .append("\nSub Subject: ").append(worksheetEntity.getSubSubject())
                .append("\nDifficulty Level: ").append(worksheetEntity.getDifficultyLevel())
                .append("\nPassage Text: ").append(worksheetEntity.getPassage().getPassageText())
                .append("\nQuestion Types: ").append(worksheetEntity.getQuestionTypesList());

        String fullPrompt = promptBuilder.toString();

        try {
            // Call OpenAI and get the response
            String response = OPENAI_CONFIG.generateWorksheetHTTPRequest(fullPrompt);
            System.out.println("Response: " + response);


            // Parse the response into a Worksheet object
            WorksheetEntity responseWorksheetEntity = parseOpenAIResponseAndCreateWorksheet(response);


            // Set additional metadata before saving
            responseWorksheetEntity.setMainSubject(worksheetEntity.getMainSubject());
            responseWorksheetEntity.setSubSubject(worksheetEntity.getSubSubject());
            responseWorksheetEntity.setDifficultyLevel(worksheetEntity.getDifficultyLevel());
            responseWorksheetEntity.setUserEntity(new UserEntity(worksheetEntity.getUserEntity().getUsername()));

            // Set Passage in Worksheet object
            responseWorksheetEntity.setPassage(new PassageEntity(
                    worksheetEntity.getPassage().getPassageTitle(),
                    worksheetEntity.getPassage().getPassageText()));

            // Persist the worksheet using DAO
            WorksheetEntity savedWorksheetEntity = worksheetDAO.createWorksheet(responseWorksheetEntity);

            if (savedWorksheetEntity == null) {
                throw new RuntimeException("Failed to save worksheet to database");
            }

            return savedWorksheetEntity;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating worksheet", e);
        }
    }

    @Override
    public CompletableFuture<WorksheetEntity> generateWorksheetAsync(WorksheetEntity worksheetEntity) {
        CompletableFuture<WorksheetEntity> future = new CompletableFuture<>();

        Task<WorksheetEntity> generateWorksheetTask = new Task<>() {
            @Override
            protected WorksheetEntity call() throws Exception {
                return generateWorksheetCallFromController(worksheetEntity);
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
                return worksheetDAO.findWorksheet(searchTerm);
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

    @Override
    public CompletableFuture<List<WorksheetEntity>> listWorksheetsAsync(int userId) {
        CompletableFuture<List<WorksheetEntity>> future = new CompletableFuture<>();
        Task<List<WorksheetEntity>> listTask = new Task<>() {
            @Override
            protected List<WorksheetEntity> call() throws Exception {
                return worksheetDAO.listWorksheets(userId);
            }
        };
        listTask.setOnSucceeded(event -> {
           List<WorksheetEntity> list = listTask.getValue();
            future.complete(list);
        });

        listTask.setOnFailed(event -> {
            future.completeExceptionally(listTask.getException());
        });

        new Thread(listTask).start();


        return future;
    }


    @Override
    public CompletableFuture<Void> deleteWorksheetAsync(int worksheetId, int userId) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        Task<Void> deleteWorksheetTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                worksheetDAO.deleteWorksheet(worksheetId, userId);
                return null; // No return value needed
            }
        };

        deleteWorksheetTask.setOnSucceeded(event -> {
            future.complete(null); // Mark the future as complete
        });

        deleteWorksheetTask.setOnFailed(event -> {
            future.completeExceptionally(deleteWorksheetTask.getException());
        });

        new Thread(deleteWorksheetTask).start();

        return future;
    }

    //================================================== FORMATTER ====================================================//
    private static WorksheetEntity parseOpenAIResponseAndCreateWorksheet(String response) {

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray choices = jsonResponse.getJSONArray("choices");

        // Get first choice (most common case)
        JSONObject firstChoice = choices.getJSONObject(0); // Message located inside choices, this choices specific to openai response not business logic
        JSONObject message = firstChoice.getJSONObject("message");

        // Extract the content
        String content = message.getString("content"); // Content located inside messages

        // Process the content to create a Worksheet
        WorksheetEntity worksheetEntity = new WorksheetEntity();
        worksheetEntity.setQuestionList(parseQuestionsFromContent(content));

        return worksheetEntity;
    }


    private static List<QuestionEntity> parseQuestionsFromContent(String content) {
        List<QuestionEntity> questionEntities = new ArrayList<>();
        String[] lines = content.split("\n");

        String currentQuestionText = null;
        String correctOption = null;
        List<ChoiceEntity> currentChoiceEntities = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            if (line.matches("^\\d+\\. .*")) {
                // If there's a previous question, add it to the list ; previous iteration extracting question
                // following iteration we are adding to questions
                if (currentQuestionText != null) {
                    questionEntities.add(new QuestionEntity(currentQuestionText, currentChoiceEntities, correctOption));

                    // Reset for the next question
                    currentChoiceEntities = new ArrayList<>();
                    correctOption = null;
                }

                // Extract question text
                currentQuestionText = line.substring(line.indexOf(". ") - 1).trim();
                System.out.println("currentQuestionText: " + currentQuestionText);
                // Extract correct option if present
                if (currentQuestionText.contains("(Correct Option:")) {
                    int start = currentQuestionText.indexOf("(Correct Option:") + 17;
                    int end = currentQuestionText.indexOf(")", start);
                    correctOption = currentQuestionText.substring(start, end).trim();
                }

                // Remove correct option text from question
                currentQuestionText = currentQuestionText.replaceAll("\\(Correct Option:.*?\\)", "").trim();
            }
            // Check for choice lines
            else if (line.matches("^[A-D]\\. .*")) {
                currentChoiceEntities.add(new ChoiceEntity(line));
            }

        }

        // Add the last question
        if (currentQuestionText != null) {
            questionEntities.add(new QuestionEntity(currentQuestionText, currentChoiceEntities, correctOption));
        }


        return questionEntities;
    }

}

