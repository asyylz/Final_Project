package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Models.Model;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.Views.UserMenuOptions;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMenuController implements Initializable {
    public ImageView avatar;
    public Text userNameAfterLogin;
    public Button generatorBtn;
    public Button worksheetBtn;
    public Button accountBtn;
    public VBox userMenu;
    public HBox userAvatarWrapper;
    public Text logoutText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Generate button  show up first with active style since its related view is active on screen
        generatorBtn.getStyleClass().add("active");
        //When ever other buttons are being clicked we are removing generateBtn's  active style
        worksheetBtn.pressedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                generatorBtn.getStyleClass().remove("active");
            }
        });
        accountBtn.pressedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                generatorBtn.getStyleClass().remove("active");
            }
        });



        addListeners();
        if (Model.getInstance().getUserName() != null) {
            userNameAfterLogin.setText(Model.getInstance().getUserName());

        }

        avatar.setOnMouseClicked(event -> {
            BooleanProperty isLogout = Utils.notifyUser("Would you like to log out ?", "Logout request", "Logout", Alert.AlertType.CONFIRMATION);

            if (isLogout.get()) {
                Stage currentStage = (Stage) avatar.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(currentStage);
                Model.getInstance().setUserName(null);
            }

            if (isLogout.get()) {
                Platform.exit(); // Closes all stages and stops JavaFX
                System.exit(0);  // JVM stops completely
            }

        });
    }


    private void addListeners() {
        generatorBtn.setOnAction(event -> onGenerator());
        worksheetBtn.setOnAction(event -> onWorksheet());
        accountBtn.setOnAction(event -> onSettings());
    }

    private void onGenerator() {
        Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.GENERATOR);
    }

    private void onWorksheet() {
        Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.WORKSHEET);
    }

    private void onSettings() {
        Model.getInstance().getViewFactory().getUserSelectMenuView().set(UserMenuOptions.SETTINGS);
    }


}
