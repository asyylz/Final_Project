package com.wgapp.worksheetgenerator.Utils;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.SubSubjectOptionsEnglish;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.List;
import java.util.Optional;

public class Utils {

    private static Timeline timeline;
    private static int timeSeconds = 1200; // 20 minutes = 1200 seconds

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
                return new SimpleBooleanProperty(true);

            }
        }
        return new SimpleBooleanProperty(false);
    }

    public static String checkSubSubject() {

        switch (Model.getInstance().getSubSubjectProperty().get()) {
            case SubSubjectOptionsEnglish.COMPREHENSION -> {
                return PromtConstants.PROMPT_BEGINNING_COMPREHENSION;
            }
            case SubSubjectOptionsEnglish.CLOZE_TEST -> {
                return PromtConstants.PROMPT_BEGINNING_CLOZE;
            }
            case SubSubjectOptionsEnglish.VOCABULARY -> {
                return PromtConstants.PROMPT_BEGINNING_VOCABULARY;
            }
            case SubSubjectOptionsEnglish.SPAG -> {
                return PromtConstants.PROMPT_BEGINNING_SPAG;
            }
            default ->
                    throw new IllegalStateException("Unexpected value: " + Model.getInstance().getSubSubjectProperty().get());
        }
    }


    /*====================================== TIMER ====================================*/
    public static void setTimer(Text timerLabel) {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = 1200; // Reset to initial time

        // Update label to show initial time
        updateTimerLabel(timerLabel);

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    timeSeconds--;
                    updateTimerLabel(timerLabel);
                    if (timeSeconds <= 0) {
                        timeline.stop();
                        // Handle time up
                        handleTimeUp();
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }

    private static void updateTimerLabel(Text timerLabel) {
        int minutes = timeSeconds / 60;
        int seconds = timeSeconds % 60;
        timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private static void handleTimeUp() {
        // What to do when time is up
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Time's Up!");
        alert.setHeaderText(null);
        alert.setContentText("Time has expired!");
        alert.showAndWait();
    }

    // Methods to control the timer
    public static void pauseTimer() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    public static void resumeTimer() {
        if (timeline != null) {
            timeline.play();
        }
    }

    public static void stopTimer() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    public static BooleanProperty notifyUser(String message, String header, String title, Alert.AlertType alertType) {
        BooleanProperty userResponse = new SimpleBooleanProperty(false); // Default is false

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);

        if (alertType == Alert.AlertType.INFORMATION) {
            alert.show();

            // Auto-close after 2 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(e -> alert.close());
            pause.play();

        } else if (alertType == Alert.AlertType.CONFIRMATION) {
            Optional<ButtonType> result = alert.showAndWait();
            userResponse.set(result.isPresent() && result.get() == ButtonType.OK); // Set property value based on user action
        } else {
            alert.showAndWait();
        }

        return userResponse;
    }


}
