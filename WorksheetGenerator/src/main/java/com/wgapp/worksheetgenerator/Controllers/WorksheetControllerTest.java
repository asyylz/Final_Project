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

    public void generateWorksheet() throws Exception {
        worksheetService.generateWorksheetAsync()
                .thenAccept(worksheet -> {
                    Platform.runLater(() -> {
                        this.worksheet = worksheet;
                        notifyObservers();
                    });
                });
    }

    private void notifyObservers() {
        for (WorksheetObserver observer : observers) {
            observer.onWorksheetGenerated(worksheet);
        }
    }
}
