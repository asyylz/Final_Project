package com.wgapp.worksheetgenerator.Controllers;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Services.OpenAIService;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorksheetController {
    private static final String PROMPT_BEGINNING;

    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        PROMPT_BEGINNING = dotenv.get("PROMPT_BEGINNING");
    }

    public static Worksheet generateWorksheet() {
        OpenAIService openAIService = new OpenAIService();
        // Get values from the model
        Model model = Model.getInstance();
        String mainSubject = model.getMainSubject().get().toString();  // Assuming this is an Enum
        String subSubject = model.getSubSubject().get().toString();    // Assuming this is an Enum
        String difficultyLevel = model.getDifficultyLevel().toString();  // Enum
        String passageText = model.getPassageContent();  // String (long text)
        //  int numberOfQuestions = model.getNumberOfQuestions();  // Integer
        List<ComprehensionQuestionTypes> questionTypes = model.getQuestionTypeList(); // List of selected question types

        // Build the prompt dynamically
        StringBuilder promptBuilder = new StringBuilder(PROMPT_BEGINNING);

        promptBuilder.append("\nMain Subject: ").append(mainSubject)
                .append("\nSub Subject: ").append(subSubject)
                .append("\nDifficulty Level: ").append(difficultyLevel)
                .append("\nPassage Text: ").append(passageText)
                //.append("\nNumber of Questions: ").append(numberOfQuestions)
                .append("\nQuestion Types: ").append(questionTypes);

        // Create the full prompt to be sent to OpenAI
        String fullPrompt = promptBuilder.toString();


        try {
            String response = openAIService.generateWorksheetHTTPRequest(fullPrompt);
            System.out.println("OpenAI Response: " + response);

            // Process the response into a Worksheet object (parse it)
            Worksheet worksheet = parseOpenAIResponse(response);

            return worksheet;  // Return the generated worksheet

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private static Worksheet parseOpenAIResponse(String response) {

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

        // Process each question from the response
        for (String line : lines) {
            if (line.matches("^\\d+\\. .*")) { // Matches lines starting with "1.", "2.", etc.
                String[] parts = line.split("\n");
                String questionText = parts[0].trim();

                // Convert the remaining parts to a list of Option objects
                List<Choice> options = Arrays.stream(parts)
                        .skip(1) // Skip the question text (first part)
                        .map(Choice::new) // Map each string to an Option object
                        .toList();

                Question question = new Question(questionText, options);

                questions.add(question);
            }
        }
        return questions;
    }
}
