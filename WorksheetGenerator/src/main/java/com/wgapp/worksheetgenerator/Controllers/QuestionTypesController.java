package com.wgapp.worksheetgenerator.Controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionTypesController implements Initializable {
    public HBox questionTypes;
    public TextArea passageTextarea;
    public Button generateBtn;
    public Button beautifyBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addListener();
    }
    
    private void addListener() {
        beautifyBtn.setOnAction(e -> onBeautifyButtonClicked());
    }

    private void onBeautifyButtonClicked() {
        String text = passageTextarea.getText();

        // Beautify the text by rearranging and formatting
        String beautifiedText = beautifyText(text);

        // Set the beautified text back into the TextArea
        passageTextarea.setText(beautifiedText);
    }

    // Beautify text by wrapping long lines and adding spacing
    private String beautifyText(String text) {
        // Example: Trim excess spaces and wrap long lines
        text = text.trim(); // Trim leading and trailing spaces
        text = text.replaceAll("\\s+", " "); // Replace multiple spaces with a single space
        //text = text.replaceAll("(?<=\\w)(?=\\p{Punct})", " "); // Add space before punctuation if needed

        StringBuilder beautified = new StringBuilder();
        int maxLineLength = 80;
        int currentLength = 0;

        for (String word : text.split(" ")) {
            if (currentLength + word.length() > maxLineLength) {
                beautified.append("\n");  // Add a line break when max length is exceeded
                currentLength = 0;
            }
            beautified.append(word).append(" ");
            currentLength += word.length() + 1;
        }

        return beautified.toString();
    }


}
