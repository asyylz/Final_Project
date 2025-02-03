package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.UserController;
import com.wgapp.worksheetgenerator.DTOs.UserDTO;
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
            String confirmPin = pinNumberConfirmField.getText();
            if (pin.equals(confirmPin)) {
                userController.setPin(new UserDTO(Model.getInstance().getUserName(), Integer.parseInt(pin)));
                pinNumberField.clear();
                pinNumberConfirmField.clear();
            }
            Utils.notifyUser("PIN successfully created.", "Pin Creation", "Success", Alert.AlertType.INFORMATION);
        });

        updatePasswordBtn.setOnAction(e -> {
            String oldPassword = oldPasswordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String newPassword = newPasswordField.getText();

            if (newPassword.equals(confirmPassword)) {
                userController.updatePassword(new UserDTO(Model.getInstance().getUserName(), oldPassword, newPassword));
                Utils.notifyUser("Password successfully updated.", "Password Update", "Success", Alert.AlertType.INFORMATION);
            }
        });

    }
}
