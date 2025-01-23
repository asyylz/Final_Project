package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Services.OpenAIService;

import com.wgapp.worksheetgenerator.Services.WorksheetService;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorksheetController {


    private final WorksheetService worksheetService;

    public WorksheetController(WorksheetService worksheetService) {
        this.worksheetService = worksheetService;
    }

    public Worksheet generateWorksheet() throws Exception {  // Either specify exact exception or rethrow
        try {
            return worksheetService.generateWorksheet();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;  // Rethrow the exception or handle it appropriately
            // OR return null; // If you want to return null in case of failure
            // OR throw new CustomException("Failed to generate worksheet", e); // If you want to wrap in custom exception
        }
    }


//    public static Worksheet generateWorksheet() {
//        OpenAIService openAIService = new OpenAIService();
//        // Get values from the model
//        Model model = Model.getInstance();
//        String mainSubject = model.getMainSubject().get().toString();  // Enum
//        String subSubject = model.getSubSubject().get().toString();    // Enum
//        String difficultyLevel = model.getDifficultyLevel().toString();  // Enum
//        String passageText = model.getPassageContent();  // String (long text)
//        //  int numberOfQuestions = model.getNumberOfQuestions();  // Integer
//        List<ComprehensionQuestionTypes> questionTypes = model.getQuestionTypeList(); // List of selected question types
//
//        // Build the prompt dynamically
//        StringBuilder promptBuilder = new StringBuilder(PROMPT_BEGINNING_COMPREHENSION);
//
//        promptBuilder
//                .append(
//                        """
//                                Use the following format for the questions:
//                                1. [Question text] ([Question type])
//                                2. [Question text] ([Question type])
//
//                                Examples of question structures:
//                                - Question 1: What does the term "quagmire" mean in the passage? (VOCABULARY)
//                                  A.[Answer Text]
//                                  B.[Answer Text]
//                                  C.[Answer Text]
//                                  D.[Answer Text]
//                                - Question 2: Why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain? (CHARACTER ANALYSIS)
//                                  A.[Answer Text]
//                                  B.[Answer Text]
//                                  C.[Answer Text]
//                                  D.[Answer Text]
//
//                                Rules:
//                                1. Ensure each question is directly related to the passage.
//                                2. Maintain a balance between difficulty levels across the questions.
//                                3. Format the output as a numbered list.
//
//                                User Options:
//                                """
//                )
//                .append("\nMain Subject: ").append(mainSubject)
//                .append("\nSub Subject: ").append(subSubject)
//                .append("\nDifficulty Level: ").append(difficultyLevel)
//                .append("\nPassage Text: ").append(passageText)
//                //.append("\nNumber of Questions: ").append(numberOfQuestions) // will be considered
//                .append("\nQuestion Types: ").append(questionTypes);
//
//        // Create the full prompt to be sent to OpenAI
//        String fullPrompt = promptBuilder.toString();
//
//
//        try {
//            String response = openAIService.generateWorksheetHTTPRequest(fullPrompt);
//            System.out.println("OpenAI Response: " + response);
//
//            // Process the response into a Worksheet object (parse it)
//            Worksheet worksheet = parseOpenAIResponseAndCreateWorksheet(response);
//
//            //  System.out.println(worksheet.getQuestionList());
//
//            // Loop over the questions in the worksheet
//            for (Question question : worksheet.getQuestionList()) {
//                System.out.println(question);  // This will call question.toString()
//            }
//
//            return worksheet;  // Return the generated worksheet
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//
//    }

//    private static Worksheet parseOpenAIResponseAndCreateWorksheet(String response) {
//
//        JSONObject jsonResponse = new JSONObject(response);
//        JSONArray choices = jsonResponse.getJSONArray("choices");
//
//        // Get first choice (most common case)
//        JSONObject firstChoice = choices.getJSONObject(0); // Message located inside choices, this choices specific to openai response not business logic
//        JSONObject message = firstChoice.getJSONObject("message");
//
//        // Extract the content
//        String content = message.getString("content"); // Content located inside messages
//
//        // Process the content to create a Worksheet
//        Worksheet worksheet = new Worksheet();
//        worksheet.setQuestionList(parseQuestionsFromContent(content));
//
//        return worksheet;
//    }

//    private static List<Question> parseQuestionsFromContent(String content) {
//        List<Question> questions = new ArrayList<>();
//        String[] lines = content.split("\n");
//
//        String currentQuestionText = null;
//        List<Choice> currentChoices = new ArrayList<>();
//
//        // Process each question from the response
//        for (String line : lines) {
//            line = line.trim();
//
//            // Check for question lines starting with "1.", "2.", etc.
//            if (line.matches("^\\d+\\. .*")) {
//                // If there's a current question, save it before starting a new one
//                if (currentQuestionText != null) {
//                    questions.add(new Question(currentQuestionText, new ArrayList<>(currentChoices)));
//                    currentChoices.clear(); // Clear choices for the next question
//                }
//
//                // Extract question text
//                currentQuestionText = line.substring(line.indexOf(".") + 1).trim();
//            }
//            // Check for choice lines starting with "A.", "B.", etc.
//            else if (line.matches("^[A-D]\\. .*")) {
//                currentChoices.add(new Choice(line));
//            }
//        }
//        // Add the last question if it exists
//        if (currentQuestionText != null) {
//            questions.add(new Question(currentQuestionText, currentChoices));
//        }
//        return questions;
//    }

}
