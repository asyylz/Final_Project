package com.wgapp.worksheetgenerator.Database;

import com.wgapp.worksheetgenerator.Models.Question;
import com.wgapp.worksheetgenerator.Models.Worksheet;

import java.util.List;

public interface IWorksheetDAO {
    public Worksheet getWorksheetById(long id);
    public List<Worksheet> getAllWorksheets();
    public List<Worksheet> getWorksheetsByTitle(String title);
    public Worksheet createWorksheet(Worksheet worksheet);
    public Worksheet updateWorksheet(Worksheet worksheet);
    public void deleteWorksheet(int id);
    public void createQuestion(Question question,int worksheetId);

}
