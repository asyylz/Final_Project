package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.DAO.Entities.WorksheetEntity;

import java.util.concurrent.CompletableFuture;

public interface WorksheetService {
    public CompletableFuture<WorksheetEntity> generateWorksheetAsync(WorksheetEntity worksheet);
    public CompletableFuture<WorksheetEntity> findWorksheetAsync(String searchTerm);
    public CompletableFuture<Void> deleteWorksheetAsync(int worksheetId, int userId);
}
