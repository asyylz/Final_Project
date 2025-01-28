package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.Choice;
import com.wgapp.worksheetgenerator.Models.Question;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class QuestionComponentController implements Initializable {

    public AnchorPane questionWrapperParent;
    public VBox choicesWrapper;
    public VBox questionWrapper;
    public Text questionText;
    public HBox choiceWrapper1;
    public RadioButton choiceRadioBtn1;
    public HBox choiceWrapper2;
    public Text choiceText1;
    public RadioButton choiceRadioBtn2;
    public Text choiceText2;
    public HBox choiceWrapper3;
    public RadioButton choiceRadioBtn3;
    public Text choiceText3;
    public HBox choiceWrapper4;
    public RadioButton choiceRadioBtn4;
    public Text choiceText4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setQuestion(Question question) {
        // Set the question data to your component's UI elements
        questionText.setText(question.getQuestionText());
        questionText.setWrappingWidth(780);

        for (int i = 0; i < choicesWrapper.getChildren().size(); i++) {
            Node node = choicesWrapper.getChildren().get(i);

            if (node instanceof HBox) {
                HBox choiceBoxSingle = (HBox) node;

               Text choiceText =(Text)choiceBoxSingle.getChildren().get(1);
                choiceText.setText(question.getChoices().get(i).getChoiceText());

            }
        }
    }
}
