package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.Utils.Utils;
import com.wgapp.worksheetgenerator.ViewFactory.UserMenuOptions;
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
import java.sql.SQLOutput;
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

        addListeners();

        // Generate button  show up first with active style since its related view is active on screen
        generatorBtn.getStyleClass().add("active");


        Model.getInstance().getViewFactory().getUserSelectMenuView().addListener((obs, oldVal, newVal) -> {

            if(newVal.equals(UserMenuOptions.GENERATOR)) {
                if (!generatorBtn.getStyleClass().contains("active")) {
                    generatorBtn.getStyleClass().add("active"); // Add class if it’s not already there
                }
            } else {
                generatorBtn.getStyleClass().remove("active"); // Remove class if condition is false
            }

            if(newVal.equals(UserMenuOptions.WORKSHEET)) { // it does not enter here
                if (!worksheetBtn.getStyleClass().contains("active")) {
                    worksheetBtn.getStyleClass().add("active"); // Add class if it’s not already there
                }
            } else {
                worksheetBtn.getStyleClass().remove("active"); // Remove class if condition is false
            }

            if(newVal.equals(UserMenuOptions.SETTINGS)) {
                if (!accountBtn.getStyleClass().contains("active")) {
                    accountBtn.getStyleClass().add("active"); // Add class if it’s not already there
                }
            } else {
                accountBtn.getStyleClass().remove("active"); // Remove class if condition is false
            }

        });


        if (Model.getInstance().getUserProperty().getUsername()!= null) {
            userNameAfterLogin.setText(Model.getInstance().getUserProperty().getUsername());

        }

        avatar.setOnMouseClicked(event -> {
            BooleanProperty isLogout = Utils.notifyUser("Would you like to log out ?", "Logout request", "Logout", Alert.AlertType.CONFIRMATION);

            if (isLogout.get()) {
                Stage currentStage = (Stage) avatar.getScene().getWindow();
                Model.getInstance().getViewFactory().closeStage(currentStage);
                Model.getInstance().getUserProperty().setUsername(null);
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
