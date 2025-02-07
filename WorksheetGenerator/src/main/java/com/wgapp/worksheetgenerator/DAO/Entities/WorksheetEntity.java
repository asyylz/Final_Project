package com.wgapp.worksheetgenerator.DAO.Entities;

import com.wgapp.worksheetgenerator.ModelsUI.Enums.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.SubSubjectOptionsEnglish;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;

import java.util.List;

public class WorksheetEntity {
    private int worksheetId;
    private MainSubjectOptions mainSubject; // Enum for main subject
    private ISubSubjectOptions subSubject; // Interface for sub-subject
    private List<QuestionEntity> questionEntityList;   // List of questions
    private List<ComprehensionQuestionTypes> questionTypesList;   // List of questions
    private DifficultyLevelOptions difficultyLevel;
    private PassageEntity passageEntity;
    private UserEntity userEntity;

    public WorksheetEntity() {

    }

    // Difficulty level
    public WorksheetEntity(MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, List<QuestionEntity> questionEntityList, DifficultyLevelOptions difficultyLevel) {
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.questionEntityList = questionEntityList;
        this.difficultyLevel = difficultyLevel;
    }

    public WorksheetEntity(int worksheetId, MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, List<QuestionEntity> questionEntityList, DifficultyLevelOptions difficultyLevel) {
        this.worksheetId = worksheetId;
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.questionEntityList = questionEntityList;
        this.difficultyLevel = difficultyLevel;
    }

    public WorksheetEntity(int worksheetId, MainSubjectOptions mainSubject, ISubSubjectOptions subSubject , DifficultyLevelOptions difficultyLevel, PassageEntity passageEntity) {
        this.worksheetId = worksheetId;
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.difficultyLevel = difficultyLevel;
        this.passageEntity = passageEntity;
    }

    public WorksheetEntity(int worksheetId, MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, DifficultyLevelOptions difficultyLevel) {
        this.worksheetId = worksheetId;
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.difficultyLevel = difficultyLevel;

    }


    public List<QuestionEntity> getQuestionList() {
        return questionEntityList;
    }

    public void setQuestionList(List<QuestionEntity> questionEntityList) {
        this.questionEntityList = questionEntityList;
    }

    public void setMainSubject(MainSubjectOptions mainSubject) {
        this.mainSubject = mainSubject;
    }

    public void setSubSubject(ISubSubjectOptions subSubject) {
        this.subSubject = subSubject;
    }

    public void setDifficultyLevel(DifficultyLevelOptions difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public MainSubjectOptions getMainSubject() {
        return mainSubject;
    }

    public ISubSubjectOptions getSubSubject() {
        return subSubject;
    }

    public DifficultyLevelOptions getDifficultyLevel() {
        return difficultyLevel;
    }

    public int getWorksheetId() {
        return worksheetId;
    }

    public void setWorksheetId(int worksheetId) {
        this.worksheetId = worksheetId;
    }

    public PassageEntity getPassage() {
        return passageEntity;
    }

    public void setPassage(PassageEntity passageEntity) {
        this.passageEntity = passageEntity;
    }

    public List<ComprehensionQuestionTypes> getQuestionTypesList() {
        return questionTypesList;
    }

    public void setQuestionTypesList(List<ComprehensionQuestionTypes> questionTypesList) {
        this.questionTypesList = questionTypesList;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

}
