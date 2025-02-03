package com.wgapp.worksheetgenerator.ModelsUI;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.DifficultyLevelOptions;
import com.wgapp.worksheetgenerator.ModelsUI.Enums.MainSubjectOptions;
import com.wgapp.worksheetgenerator.ViewFactory.ISubSubjectOptions;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;


public class WorksheetProperty {
    private  IntegerProperty id = new SimpleIntegerProperty();
    private  ObjectProperty<MainSubjectOptions> mainSubject = new SimpleObjectProperty<>();
    private  ObjectProperty<ISubSubjectOptions> subSubject = new SimpleObjectProperty<>();
    private  ObjectProperty<DifficultyLevelOptions> diffLevel = new SimpleObjectProperty<>();
    private  ListProperty<QuestionProperty> questionList = new SimpleListProperty<>(FXCollections.observableArrayList());
    private PassageProperty passage = new PassageProperty();
    private ListProperty<UserAnswer> userAnswerList = new SimpleListProperty<>(FXCollections.observableArrayList());


    public WorksheetProperty(IntegerProperty id,
                             MainSubjectOptions mainSubject,
                             ISubSubjectOptions subSubject,
                             DifficultyLevelOptions diffLevel,
                             ListProperty<QuestionProperty> questionList,
                             PassageProperty passage) {
        this.id = id;
        this.mainSubject.set(mainSubject);
        this.subSubject.set(subSubject);
        this.diffLevel.set(diffLevel);
        this.questionList = questionList;
        this.passage = passage;
    }

    public WorksheetProperty() {
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public MainSubjectOptions getMainSubject() {
        return mainSubject.get();
    }

    public ObjectProperty<MainSubjectOptions> mainSubjectProperty() {
        return mainSubject;
    }

    public ISubSubjectOptions getSubSubject() {
        return subSubject.get();
    }

    public ObjectProperty<ISubSubjectOptions> subSubjectProperty() {
        return subSubject;
    }

    public DifficultyLevelOptions getDiffLevel() {
        return diffLevel.get();
    }

    public ObjectProperty<DifficultyLevelOptions> diffLevelProperty() {
        return diffLevel;
    }

    public List<QuestionProperty> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(ListProperty<QuestionProperty> questionList) {
        this.questionList = questionList;
    }

    public PassageProperty passageProperty() {
        return passage;
    }

    public void setPassage(PassageProperty passage) {
        this.passage = passage;
    }

    public ObservableList<UserAnswer> getUserAnswerList() {
        return userAnswerList.get();
    }

    public ListProperty<UserAnswer> userAnswerListProperty() {
        return userAnswerList;
    }

    public void setUserAnswerList(ObservableList<UserAnswer> userAnswerList) {
        this.userAnswerList.set(userAnswerList);
    }

    public void setMainSubject(MainSubjectOptions mainSubject) {
        this.mainSubject.set(mainSubject);
    }

    public void setSubSubject(ISubSubjectOptions subSubject) {
        this.subSubject.set(subSubject);
    }

    public void setDiffLevel(DifficultyLevelOptions diffLevel) {
        this.diffLevel.set(diffLevel);
    }

    public void removeQuestionsFromList() {
        this.questionList.clear();
    }
}
