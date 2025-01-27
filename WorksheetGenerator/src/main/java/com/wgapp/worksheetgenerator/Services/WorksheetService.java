package com.wgapp.worksheetgenerator.Services;


import com.wgapp.worksheetgenerator.Database.WorksheetDAOImpl;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class WorksheetService {
    private static final String PROMPT_BEGINNING_COMPREHENSION;

    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        PROMPT_BEGINNING_COMPREHENSION = dotenv.get("PROMPT_BEGINNING_COMPREHENSION");
    }

    private final OpenAIService openAIService;
    private final WorksheetDAOImpl worksheetDAO;

    public WorksheetService(OpenAIService openAIService, WorksheetDAOImpl worksheetDAO) {
        this.openAIService = openAIService;
        this.worksheetDAO = worksheetDAO;
    }


    public Worksheet generateWorksheetCallFromController() {
        // Fetch values from the model
        Model model = Model.getInstance();
        MainSubjectOptions mainSubject = model.getMainSubject().get();
        ISubSubjectOptions subSubject = model.getSubSubject().get();
        DifficultyLevelOptions difficultyLevel = model.getDifficultyLevel();
        String passageText = model.getPassageContent();
        String passageTitle = model.getPassageTitle();

        List<ComprehensionQuestionTypes> questionTypes = model.getQuestionTypeList();

        // Build the prompt
        StringBuilder promptBuilder = new StringBuilder(PROMPT_BEGINNING_COMPREHENSION);
        promptBuilder
                .append("""
                        Use the following format for the questions:
                        1. [Question text] ([Question type]) ([Correct Option: X)]
                        2. [Question text] ([Question type]) ([Correct Option: X)]
                        
                        Examples of question structures:
                        - Question 1: What does the term "quagmire" mean in the passage? (VOCABULARY)(Correct Option: A)
                          A.[Option Text]
                          B.[Option Text]
                          C.[Option Text]
                          D.[Option Text]
                        - Question 2: Why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain? (CHARACTER ANALYSIS)(Correct Option: B)
                          A.[Option Text]
                          B.[Option Text]
                          C.[Option Text]
                          D.[Option Text]
                        
                        Rules:
                        1. Ensure each question is directly related to the passage.
                        2. Maintain a balance between difficulty levels across the questions.
                        3. Format the output as a numbered list.
                        User Options:
                        """)
                .append("\nMain Subject: ").append(mainSubject.toString())
                .append("\nSub Subject: ").append(subSubject.toString())
                .append("\nDifficulty Level: ").append(difficultyLevel.toString())
                .append("\nPassage Text: ").append(passageText)
                .append("\nQuestion Types: ").append(questionTypes);

        String fullPrompt = promptBuilder.toString();

        try {
            // Call OpenAI and get the response
            String response = openAIService.generateWorksheetHTTPRequest(fullPrompt);
            System.out.println("Response: " + response);


            // Parse the response into a Worksheet object
            Worksheet worksheet = parseOpenAIResponseAndCreateWorksheet(response);


            // Set additional metadata before saving
            worksheet.setMainSubject(mainSubject);
            worksheet.setSubSubject(subSubject);
            worksheet.setDifficultyLevel(difficultyLevel);

            // Set Passage in Worksheet object
            worksheet.setPassage(new Passage( passageTitle, passageText)); // at this point my id is not available since needs to  come from db

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
                currentQuestionText = line.substring(line.indexOf(".") + 1).trim();
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

}

