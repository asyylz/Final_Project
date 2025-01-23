package com.wgapp.worksheetgenerator.Models;

import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;

import java.util.List;

public class Worksheet {
    private int worksheetId;
    private MainSubjectOptions mainSubject; // Enum for main subject
    private ISubSubjectOptions subSubject; // Interface for sub-subject
    private List<Question> questionList;   // List of questions
    private DifficultyLevelOptions difficultyLevel;

    public Worksheet( ) {

    }
    // Difficulty level

    public Worksheet(MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, List<Question> questionList, DifficultyLevelOptions difficultyLevel) {
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.questionList = questionList;
        this.difficultyLevel = difficultyLevel;
    }

    public Worksheet(int worksheetId, MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, List<Question> questionList, DifficultyLevelOptions difficultyLevel) {
        this.worksheetId = worksheetId;
        this.mainSubject = mainSubject;
        this.subSubject = subSubject;
        this.questionList = questionList;
        this.difficultyLevel = difficultyLevel;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
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
}
