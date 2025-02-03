package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.ModelsUI.Worksheet;

import java.util.concurrent.CompletableFuture;

public interface WorksheetService {
    public CompletableFuture<Worksheet> generateWorksheetAsync();
    public CompletableFuture<Worksheet> findWorksheetAsync(String searchTerm);
}
