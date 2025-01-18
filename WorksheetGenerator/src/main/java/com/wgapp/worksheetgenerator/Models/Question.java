package com.wgapp.worksheetgenerator.Models;

import java.util.List;

public class Question {
    private String questionText;
    private List<Choice> options;

    public Question(String questionText, List<Choice> options) {
        this.questionText = questionText;
        this.options = options;
    }
}
