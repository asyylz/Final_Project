package com.wgapp.worksheetgenerator.DAO;

import com.wgapp.worksheetgenerator.DAO.Entities.ChoiceEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.PassageEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.QuestionEntity;
import com.wgapp.worksheetgenerator.DAO.Entities.WorksheetEntity;

import java.util.List;

public interface WorksheetDAO {
    public WorksheetEntity getWorksheetById(long id);
    public List<WorksheetEntity> getAllWorksheets();
    public WorksheetEntity findWorksheet(String title);
    public WorksheetEntity createWorksheet(WorksheetEntity worksheetEntity);
    public WorksheetEntity updateWorksheet(WorksheetEntity worksheetEntity);
    public void deleteWorksheet(int id);
    public void createQuestion(QuestionEntity questionEntity, int worksheetId);
    public void createChoices(ChoiceEntity choiceEntity, int questionId);
    public  void createPassage(PassageEntity passageEntity, int worksheetId);

}
