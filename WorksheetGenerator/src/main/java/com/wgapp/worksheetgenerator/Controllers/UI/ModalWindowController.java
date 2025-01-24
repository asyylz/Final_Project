package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.ComprehensionQuestionTypes;
import com.wgapp.worksheetgenerator.Models.Model;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.property.ListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
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
            //Model.getInstance().setQuestionTypeList(ComprehensionQuestionTypes.values());
            applyScaleTransitionOnHide(stage);

        });
    }

    private void applyScaleTransitionOnHide(Stage stage) {
        FadeTransition transition = new FadeTransition(Duration.millis(300), modalWindow);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setInterpolator(Interpolator.LINEAR);
        transition.play();
        // Apply the scale transition on hiding the modal
//        ScaleTransition st = new ScaleTransition(Duration.millis(200), modalWindow);
//        st.setInterpolator(javafx.animation.Interpolator.EASE_BOTH);
//        st.setFromX(1);  // Starting scale
//        st.setFromY(1);  // Starting scale
//        st.setToX(0);    // Scale to 0 when hiding
//        st.setToY(0);    // Scale to 0 when hiding
//        // Once the transition finishes, close the stage
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
