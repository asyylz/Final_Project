package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import javafx.concurrent.Task;


public class WorksheetController {


    private final WorksheetService worksheetService;

    public WorksheetController(WorksheetService worksheetService) {
        this.worksheetService = worksheetService;
    }

    public Worksheet generateWorksheet() throws Exception {  // Either specify exact exception or rethrow
        try {
            return worksheetService.generateWorksheetCallFromController();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;  // Rethrow the exception or handle it appropriately
            // OR return null; // If you want to return null in case of failure
            // OR throw new CustomException("Failed to generate worksheet", e); // If you want to wrap in custom exception
        }
    }

//    public Worksheet generateWorksheet(Worksheet worksheet) throws Exception {
//        // Show the loading indicator
//       // loadingIndicatorComponent.setVisible(true);
//
//        // Create a background task
//        Task<Void> generateWorksheetTask = new Task<>() {
//            @Override
//            protected Void call() throws Exception {
//                // Simulate worksheet generation (replace with actual logic)
//                worksheetController.generateWorksheet();
//                return null;
//            }
//        };
//
//        // Handle success
//        generateWorksheetTask.setOnSucceeded(event -> {
//           // loadingIndicatorComponent.setVisible(false);
//            // Handle successful completion (e.g., show a success message)
//        });
//
//        // Handle failure
//        generateWorksheetTask.setOnFailed(event -> {
//           // loadingIndicatorComponent.setVisible(false);
//            Throwable error = generateWorksheetTask.getException();
//            error.printStackTrace();
//            // Show error to user
//        });
//
//        // Run the task in a background thread
//        new Thread(generateWorksheetTask).start();
//    }


}
