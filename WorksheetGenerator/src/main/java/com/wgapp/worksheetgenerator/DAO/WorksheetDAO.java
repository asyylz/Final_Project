package com.wgapp.worksheetgenerator.DAO;

import com.wgapp.worksheetgenerator.ModelsUI.Choice;
import com.wgapp.worksheetgenerator.ModelsUI.Passage;
import com.wgapp.worksheetgenerator.ModelsUI.Question;
import com.wgapp.worksheetgenerator.ModelsUI.Worksheet;

import java.util.List;

public interface WorksheetDAO {
    public Worksheet getWorksheetById(long id);
    public List<Worksheet> getAllWorksheets();
    public Worksheet findWorksheet(String title);
    public Worksheet createWorksheet(Worksheet worksheet);
    public Worksheet updateWorksheet(Worksheet worksheet);
    public void deleteWorksheet(int id);
    public void createQuestion(Question question,int worksheetId);
    public void createChoices(Choice choice,int questionId);
    public  void createPassage(Passage passage, int worksheetId);

}
