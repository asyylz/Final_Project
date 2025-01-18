package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Services.OpenAIService;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class WorksheetController {
    private static final String PROMPT_BEGINNING;

    static {
        // Load environment variables from .env file
        Dotenv dotenv = Dotenv.load();
        PROMPT_BEGINNING = dotenv.get("PROMPT_BEGINNING");
    }

    public static void generateWorksheet() {
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
        //System.out.println(fullPrompt);

        try {
            String response = openAIService.generateWorksheet(fullPrompt);
            System.out.println("OpenAI Response: " + response);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
