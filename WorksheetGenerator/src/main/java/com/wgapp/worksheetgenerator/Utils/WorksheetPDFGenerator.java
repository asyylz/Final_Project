package com.wgapp.worksheetgenerator.Utils;

import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;

import java.io.File;
import java.io.FileNotFoundException;

public class WorksheetPDFGenerator {

    public static void saveWorksheetAsPDF(String worksheetTitle, String passage, java.util.List<String> questions,String filePath) {
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

}
