package com.wgapp.worksheetgenerator.Services;

import com.wgapp.worksheetgenerator.Models.Worksheet;

import java.util.concurrent.CompletableFuture;

public interface IService {
    public CompletableFuture<Worksheet> generateWorksheetAsync();
    public CompletableFuture<Worksheet> findWorksheetAsync(String searchTerm);
}
