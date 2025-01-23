package com.wgapp.worksheetgenerator.Controllers;
import com.wgapp.worksheetgenerator.Models.*;
import com.wgapp.worksheetgenerator.Services.WorksheetService;


public class WorksheetController {


    private final WorksheetService worksheetService;

    public WorksheetController(WorksheetService worksheetService) {
        this.worksheetService = worksheetService;
    }

    public Worksheet generateWorksheet() throws Exception {  // Either specify exact exception or rethrow
        try {
            return worksheetService.generateWorksheet();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;  // Rethrow the exception or handle it appropriately
            // OR return null; // If you want to return null in case of failure
            // OR throw new CustomException("Failed to generate worksheet", e); // If you want to wrap in custom exception
        }
    }


}
