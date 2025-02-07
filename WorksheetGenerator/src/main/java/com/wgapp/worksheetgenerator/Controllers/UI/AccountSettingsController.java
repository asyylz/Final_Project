package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.UserController;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;


public class AccountSettingsController implements Initializable {
    public Button createPinBtn;
    public PasswordField pinNumberField;
    public PasswordField pinNumberConfirmField;
    public PasswordField oldPasswordField;
    public PasswordField newPasswordField;
    public PasswordField confirmPasswordField;
    public Button updatePasswordBtn;

    private final UserController userController = new UserController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        createPinBtn.setOnAction(e -> {
            String pin = pinNumberField.getText();
            System.out.println("pin: " + pin);
            String confirmPin = pinNumberConfirmField.getText();
            if (pin.equals(confirmPin)) {
                // First we set value to be able to call pin. creation method
                Model.getInstance().getUserProperty().setPinNumber(Integer.parseInt(pin));
                // Here we are both calling and setting the return value
                Model.getInstance().setUserProperty(userController.setPin(Model.getInstance().getUserProperty()));


                pinNumberField.clear();
                pinNumberConfirmField.clear();
            }
            Utils.notifyUser("PIN successfully updated.", "PIN UPDATE", "Success", Alert.AlertType.INFORMATION);
        });

        updatePasswordBtn.setOnAction(e -> {
            String oldPassword = oldPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String newPassword = newPasswordField.getText();

            if (newPassword.equals(confirmPassword)) {
                Model.getInstance().getUserProperty().setPassword(oldPassword);
                Model.getInstance().getUserProperty().setNewPassword(newPassword);
                userController.updatePassword(Model.getInstance().getUserProperty());
                oldPasswordField.clear();
                confirmPasswordField.clear();
                newPasswordField.clear();
            }
            Utils.notifyUser("Password successfully updated.", "PASSWORD UPDATE", "Success", Alert.AlertType.INFORMATION);
        });

    }
}
