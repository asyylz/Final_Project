package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.DAO.Entities.WorksheetEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface WorksheetService {
    public CompletableFuture<WorksheetEntity> generateWorksheetAsync(WorksheetEntity worksheet);

    public CompletableFuture<WorksheetEntity> findWorksheetAsync(String searchTerm, int userId);
    public CompletableFuture<WorksheetEntity> findWorksheetAsync(int worksheetId);

    public CompletableFuture<Void> deleteWorksheetAsync(int worksheetId, int userId);

    public CompletableFuture<List<WorksheetEntity>> listWorksheetsAsync(int userId);
}
