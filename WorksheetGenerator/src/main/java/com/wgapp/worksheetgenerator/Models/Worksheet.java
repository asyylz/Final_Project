package com.wgapp.worksheetgenerator.Models;

import com.wgapp.worksheetgenerator.Views.ISubSubjectOptions;

import java.util.List;

public class Worksheet {
    private MainSubjectOptions mainSubject; // Enum for main subject
    private ISubSubjectOptions subSubject; // Interface for sub-subject
    private List<Question> questionList;   // List of questions
    private String difficultyLevel;

    public Worksheet( ) {

    }
    // Difficulty level

    public Worksheet(MainSubjectOptions mainSubject, ISubSubjectOptions subSubject, List<Question> questionList, String difficultyLevel) {
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
}
