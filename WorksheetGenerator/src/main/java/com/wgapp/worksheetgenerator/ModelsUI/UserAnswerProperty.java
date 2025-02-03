package com.wgapp.worksheetgenerator.ModelsUI;

import javafx.beans.property.*;

public class UserAnswerProperty {

    private final IntegerProperty questionId = new SimpleIntegerProperty();
    private final StringProperty answer = new SimpleStringProperty();
    private final IntegerProperty questionIndex = new SimpleIntegerProperty();
}
