package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Enums.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ModalWindowController implements Initializable {
    public Button okBtn;
    public AnchorPane modalWindow;
    public Text messageText;
    public Text modalTypeTitle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set font family Oswald
        Font.loadFont(GeneratorWindowController.class.getResourceAsStream("/Fonts/Oswald/Oswald-VariableFont_wght.ttf"), 12);
        modalWindow.setStyle("-fx-font-family: 'Oswald'; -fx-font-size: 14px;");

        // Apply ScaleTransition on modal window
        applyScaleTransition();

        okBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            Stage stage = (Stage) modalWindow.getScene().getWindow();
            applyScaleTransitionOnHide(stage);

            if (Model.getInstance().getWorksheetPropertyForGeneration().getQuestionTypeList().isEmpty())
                for (ComprehensionQuestionTypes questionType : ComprehensionQuestionTypes.values()) {
                    Model.getInstance().getWorksheetPropertyForGeneration().addQuestionType(questionType);
                }

        });
    }

    private void applyScaleTransitionOnHide(Stage stage) {
        FadeTransition transition = new FadeTransition(Duration.millis(300), modalWindow);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
        transition.setOnFinished(e -> Model.getInstance().getViewFactory().closeStage(stage));
        transition.play();
    }

    private void applyScaleTransition() {
        FadeTransition transition = new FadeTransition(Duration.millis(500), modalWindow);
        transition.setFromValue(0.0);
        transition.setToValue(1.0);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
    }

    public void setMessageText(String messageText) {
        this.messageText.setText(messageText);
    }
}
