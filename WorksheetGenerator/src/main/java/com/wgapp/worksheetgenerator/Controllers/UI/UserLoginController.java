package com.wgapp.worksheetgenerator.Controllers.UI;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController  implements Initializable {
    public AnchorPane loginWindowParent;
    public Pane loginSectionWrapper;
    public TextField userNameField;
    public TextField passwordField;
    public Button loginBtn;
    public Button registerBtn;
    public Text confirmPasswordText;
    public TextField confirmPasswordField;
    public HBox loginButtonsWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmPasswordText.setVisible(false);
        confirmPasswordField.setVisible(false);
        loginButtonsWrapper.setTranslateY(-50);
        loginSectionWrapper.setTranslateY(50);

        registerBtn.setOnAction(e ->{
            confirmPasswordText.setVisible(true);
            confirmPasswordField.setVisible(true);
            loginButtonsWrapper.setTranslateY(10);
            loginSectionWrapper.setTranslateY(10);
        });
        loginBtn.setOnAction(e ->{
            confirmPasswordText.setVisible(false);
            confirmPasswordField.setVisible(false);
            loginButtonsWrapper.setTranslateY(-50);
            loginSectionWrapper.setTranslateY(50);
        });
    }
}
