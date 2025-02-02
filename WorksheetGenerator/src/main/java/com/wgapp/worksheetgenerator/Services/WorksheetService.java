package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.Database.WorksheetDAOImpl;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import io.github.cdimascio.dotenv.Dotenv;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public class WorksheetService implements IService {
    //private static final String PROMPT_BEGINNING_COMPREHENSION;
    private static final OpenAIService openaiService = new OpenAIService();
    private static final WorksheetDAOImpl worksheetDAO = new WorksheetDAOImpl();

    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
       // PROMPT_BEGINNING_COMPREHENSION = dotenv.get("PROMPT_BEGINNING_COMPREHENSION");
    }

//    private final OpenAIService openAIService;
//    private final WorksheetDAOImpl worksheetDAO;
//
//    public WorksheetService(OpenAIService openAIService, WorksheetDAOImpl worksheetDAO) {
//        this.openAIService = openAIService;
//        this.worksheetDAO = worksheetDAO;
//    }


    public WorksheetService() {
    }

    public Worksheet generateWorksheetCallFromController() {
        // Fetch values from the model
        Model model = Model.getInstance();
        MainSubjectOptions mainSubject = model.getMainSubjectProperty().get();
        ISubSubjectOptions subSubject = model.getSubSubjectProperty().get();
        DifficultyLevelOptions difficultyLevel = model.getDifficultyLevel();
        String passageText = model.getPassageContent();
        String passageTitle = model.getPassageTitle();

        List<ComprehensionQuestionTypes> questionTypes = model.getQuestionTypeList();

        // We are  starting to build our prompt  with constant related to Model
        String initialPrompt = Utils.checkSubSubject();

        // Build the prompt starting from initial prompt
        StringBuilder promptBuilder = new StringBuilder(initialPrompt);
        promptBuilder
                .append("\nMain Subject: ").append(mainSubject.toString())
                .append("\nSub Subject: ").append(subSubject.toString())
                .append("\nDifficulty Level: ").append(difficultyLevel.toString())
                .append("\nPassage Text: ").append(passageText)
                .append("\nQuestion Types: ").append(questionTypes);

        String fullPrompt = promptBuilder.toString();

        try {
            // Call OpenAI and get the response
            String response = openaiService.generateWorksheetHTTPRequest(fullPrompt);
            System.out.println("Response: " + response);


            // Parse the response into a Worksheet object
            Worksheet worksheet = parseOpenAIResponseAndCreateWorksheet(response);


            // Set additional metadata before saving
            worksheet.setMainSubject(mainSubject);
            worksheet.setSubSubject(subSubject);
            worksheet.setDifficultyLevel(difficultyLevel);

            // Set Passage in Worksheet object
            worksheet.setPassage(new Passage(passageTitle, passageText)); // at this point my id is not available since needs to  come from db

            // Persist the worksheet using DAO
            Worksheet savedWorksheet = worksheetDAO.createWorksheet(worksheet);

            if (savedWorksheet == null) {
                throw new RuntimeException("Failed to save worksheet to database");
            }

            return savedWorksheet;


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error generating worksheet", e);
        }
    }

    private static Worksheet parseOpenAIResponseAndCreateWorksheet(String response) {

        JSONObject jsonResponse = new JSONObject(response);
        JSONArray choices = jsonResponse.getJSONArray("choices");

        // Get first choice (most common case)
        JSONObject firstChoice = choices.getJSONObject(0); // Message located inside choices, this choices specific to openai response not business logic
        JSONObject message = firstChoice.getJSONObject("message");

        // Extract the content
        String content = message.getString("content"); // Content located inside messages

        // Process the content to create a Worksheet
        Worksheet worksheet = new Worksheet();
        worksheet.setQuestionList(parseQuestionsFromContent(content));

        return worksheet;
    }

    private static List<Question> parseQuestionsFromContent(String content) {
        List<Question> questions = new ArrayList<>();
        String[] lines = content.split("\n");

        String currentQuestionText = null;
        String correctOption = null;
        List<Choice> currentChoices = new ArrayList<>();

        for (String line : lines) {
            line = line.trim();

            if (line.matches("^\\d+\\. .*")) {
                // If there's a previous question, add it to the list ; previous iteration extracting question following iteration we are adding to questions
                if (currentQuestionText != null) {
                    questions.add(new Question(currentQuestionText, currentChoices, correctOption));

                    // Reset for the next question
                    currentChoices = new ArrayList<>();
                    correctOption = null;
                }

                // Extract question text
                //currentQuestionText = line.substring(line.indexOf(".") + 1).trim();
                currentQuestionText = line.substring(line.indexOf(". ") -1).trim();
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
                currentChoices.add(new Choice(line));
            }

        }

        // Add the last question
        if (currentQuestionText != null) {
            questions.add(new Question(currentQuestionText, currentChoices, correctOption));
        }


        return questions;
    }

    @Override
    public CompletableFuture<Worksheet> generateWorksheetAsync() {
        CompletableFuture<Worksheet> future = new CompletableFuture<>();

        Task<Worksheet> generateWorksheetTask = new Task<>() {
            @Override
            protected Worksheet call() throws Exception {
                return generateWorksheetCallFromController();
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
                return worksheetDAO.findWorksheet(searchTerm);
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

