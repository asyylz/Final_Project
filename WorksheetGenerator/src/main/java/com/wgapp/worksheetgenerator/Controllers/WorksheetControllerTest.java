package com.wgapp.worksheetgenerator.Controllers;

import com.wgapp.worksheetgenerator.Models.Worksheet;
import com.wgapp.worksheetgenerator.Services.MockService;
import com.wgapp.worksheetgenerator.Services.WorksheetService;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class WorksheetControllerTest {
    private final MockService mockService = new MockService();
    private final WorksheetService worksheetService = new WorksheetService();
    private Worksheet worksheet;
    private List<WorksheetObserver> observers = new ArrayList<>();

    public interface WorksheetObserver {
        void onWorksheetGenerated(Worksheet worksheet);
    }

    public void addObserver(WorksheetObserver observer) {
        observers.add(observer);
    }

    //    public void generateWorksheet() throws Exception {
//
//        try {
//            mockService.generateWorksheetAsync()
//                    .thenAccept(worksheet -> {
//                        Platform.runLater(() -> {
//                            this.worksheet = worksheet;
//                            notifyObservers();
//                        });
//                    });
//        }catch (Exception e){
//
//        }
//
//    }
    public void generateWorksheet() {
        worksheetService.generateWorksheetAsync()
                .thenAccept(worksheet -> {
                    try {
                        Platform.runLater(() -> {
                            this.worksheet = worksheet;
                            notifyObservers();
                        });
                    } catch (Exception e) {
                        System.err.println("Error updating worksheet: " + e.getMessage());
                    }
                })
                .exceptionally(ex -> {
                    System.err.println("Error generating worksheet: " + ex.getMessage());
                    return null;  // Returning null to handle failure
                });
    }

    private void notifyObservers() {
        for (WorksheetObserver observer : observers) {
            observer.onWorksheetGenerated(worksheet);
        }
    }
}
