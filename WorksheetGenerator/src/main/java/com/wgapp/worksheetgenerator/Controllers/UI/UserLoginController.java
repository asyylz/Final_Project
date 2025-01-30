package com.wgapp.worksheetgenerator.Controllers.UI;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController  implements Initializable {
    public AnchorPane loginWindowParent;
    public Pane loginSectionWrapper;
    public TextField userNameField;
    public TextField passwordField;
    public Button loginBtn;
    public Button registerBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
