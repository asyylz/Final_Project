package com.wgapp.worksheetgenerator.Utils;

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.wgapp.worksheetgenerator.Models.Choice;
import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Models.Worksheet;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.stream.Collectors;

public class WorksheetPDFGenerator {

    public static void saveWorksheetAsPDF(String worksheetTitle, String passage, java.util.List<String> questions, String filePath) {
        System.out.println(filePath);

        try {
            // Create PDF Writer
            PdfWriter writer = new PdfWriter(new File(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add title
            document.add(new Paragraph(new Text(worksheetTitle).setBold().setFontSize(16)));

            // Add passage
            document.add(new Paragraph("Passage:").setBold());
            document.add(new Paragraph(passage));

            // Add questions
            document.add(new Paragraph("Questions:").setBold());
            List questionList = new List();
            for (String question : questions) {
                questionList.add(new ListItem(question));
            }
            document.add(questionList);

            // Close document
            document.close();
            System.out.println("Worksheet saved as PDF: " + filePath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void downloadWorksheetHandler(Window currentWindow) throws IOException {
        try {
            Worksheet worksheet = Model.getInstance().getWorksheet();

            FileChooser newFileChooser = new FileChooser();
            newFileChooser.setTitle("Save as a Pdf file");
            newFileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );
            File file = newFileChooser.showSaveDialog(currentWindow);

            // Get worksheet title and passage
            String title = worksheet.getPassage().getPassageTitle();
            String passage = worksheet.getPassage().getPassageText();
            // List<Choice> listOfChoices = worksheet.getQuestionList()

            if (file != null) {
                // Convert Question objects to strings including their choices
                java.util.List<String> questionTexts = worksheet.getQuestionList().stream()
                        .map(question -> {
                            StringBuilder questionWithChoices = new StringBuilder();
                            questionWithChoices.append(question.getQuestionText()).append("\n");

                            // Add each choice
                            java.util.List<Choice> choices = question.getChoices();
                            for (int i = 0; i < choices.size(); i++) {
                                //   char choiceLetter = (char) ('A' + i);  // Convert 0->A, 1->B, etc.
                                questionWithChoices
                                        //.append(choiceLetter)
                                        //.append(") ")
                                        .append(choices.get(i).getChoiceText())
                                        .append("\n");
                            }
                            questionWithChoices.append("\n"); // Extra line break

                            return questionWithChoices.toString();
                        })
                        .collect(Collectors.toList());

                // Generate the PDF
                WorksheetPDFGenerator.saveWorksheetAsPDF(
                        title,
                        passage,
                        questionTexts,
                        file.getAbsolutePath()
                );
            }
        } catch (IOException e) {
            // Show error alert instead of throwing
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Worksheet Generation Failed");
            alert.setContentText("An error occurred while generating the worksheet.");
            alert.showAndWait();
        }
    }
}
