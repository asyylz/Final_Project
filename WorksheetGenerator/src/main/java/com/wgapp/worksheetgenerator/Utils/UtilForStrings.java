package com.wgapp.worksheetgenerator.Utils;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.Question;
import com.wgapp.worksheetgenerator.Models.UserAnswer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.List;

public class UtilForStrings {
    public static BooleanProperty hasQuestionType(String checkBoxTextValue) {

        List<ComprehensionQuestionTypes> list = Model.getInstance().getQuestionTypeList();

        String placeHolderForTextValue = "";

        switch (checkBoxTextValue) {
            case "Inferential":
                placeHolderForTextValue = ComprehensionQuestionTypes.INFERENTIAL.toString();
                break;
            case "Literal":
                placeHolderForTextValue = ComprehensionQuestionTypes.LITERAL.toString();
                break;
            case "Summarizing":
                placeHolderForTextValue = ComprehensionQuestionTypes.SUMMARIZING.toString();
                break;
            case "Vocabulary":
                placeHolderForTextValue = ComprehensionQuestionTypes.VOCABULARY.toString();
                break;
            case "Character Analysis":
                placeHolderForTextValue = ComprehensionQuestionTypes.CHARACTER_ANALYSIS.toString();
                break;
            case "Tone and Mood":
                placeHolderForTextValue = ComprehensionQuestionTypes.TONE_AND_MOOD.toString();
                break;
            case "Author’s Purpose/Intention":
                placeHolderForTextValue = ComprehensionQuestionTypes.AUTHORS_PURPOSE.toString();
                break;
            case "Critical Thinking or Opinion-Based":
                placeHolderForTextValue = ComprehensionQuestionTypes.CRITICAL_THINKING.toString();
                break;
            case "Structure":
                placeHolderForTextValue = ComprehensionQuestionTypes.STRUCTURE.toString();
        }

        for (ComprehensionQuestionTypes comprehensionQuestionType : list) {
            if (comprehensionQuestionType.toString().equals(placeHolderForTextValue)) {
                //System.out.println("fromutil string" + placeHolderForTextValue);

                return new SimpleBooleanProperty(true);

            }

        }
        return new SimpleBooleanProperty(false);
    }

    public static Boolean isUserAnswerCorrect(String correctAnswer) {

        for (UserAnswer answer : Model.getInstance().getUserAnswersList()) {
            if (answer.getAnswer().equals(correctAnswer)) {
                return true;
            }
        }
        return false;

    }
}
