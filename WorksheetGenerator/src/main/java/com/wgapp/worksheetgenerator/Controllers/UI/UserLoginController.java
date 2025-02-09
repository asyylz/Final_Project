package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.UserController;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.UserProperty;
import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginController implements Initializable {
    public AnchorPane loginWindowParent;
    public Pane loginSectionWrapper;
    public TextField userNameField;
    public TextField passwordField;
    public Button loginBtn;
    public Button registerBtn;
    public Text confirmPasswordText;
    public TextField confirmPasswordField;
    public HBox loginButtonsWrapper;
    public BooleanProperty isRegisterMode = new SimpleBooleanProperty(false);

    private final UserController userController = new UserController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        userNameField.setText("asiye86");
//        passwordField.setText("6946224B");

        confirmPasswordText.setVisible(false);
        confirmPasswordField.setVisible(false);
        loginButtonsWrapper.setTranslateY(-50);
        loginSectionWrapper.setTranslateY(50);

        registerBtn.setOnAction(e -> {
            onRegisterHandler();
        });
        // Enter key
        registerBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Trigger login function when Enter key is pressed
                onRegisterHandler();
            }
        });
        loginBtn.setOnAction(e -> {
            onLoginHandler();

        });
        // Enter key
        loginBtn.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                // Trigger login function when Enter key is pressed
                onLoginHandler();
            }
        });

        registerBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                isRegisterMode.get() && (
                                        userNameField.textProperty().isEmpty().get() ||
                                                passwordField.textProperty().isEmpty().get() ||
                                                confirmPasswordField.textProperty().isEmpty().get()
                                ),
                        isRegisterMode, // Dependency on isRegisterMode
                        userNameField.textProperty(),
                        passwordField.textProperty(),
                        confirmPasswordField.textProperty()
                )
        );


        loginBtn.disableProperty().bind(
                Bindings.createBooleanBinding(() ->
                                !isRegisterMode.get() && (
                                        userNameField.textProperty().isEmpty().get() ||
                                                passwordField.textProperty().isEmpty().get()
                                ),
                        isRegisterMode, // Dependency on isRegisterMode
                        userNameField.textProperty(),
                        passwordField.textProperty()
                )
        );

    }

    private void onRegisterHandler() {
        if (!isRegisterMode.get()) {
            // First click: Show the confirm password field
            confirmPasswordText.setVisible(true);
            confirmPasswordField.setVisible(true);
            loginButtonsWrapper.setTranslateY(10);
            loginSectionWrapper.setTranslateY(10);
            isRegisterMode.set(true); // Set flag to true

            // Empty fields  if login time fields were not empty
            userNameField.setText("");
            passwordField.setText("");
            confirmPasswordField.setText("");

        } else {
            // Second click: Actually register the user
            String username = userNameField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();

            // Check if passwords match
            if (!password.equals(confirmPassword)) {
                System.out.println("Passwords do not match!");
                return; // Stop registration if passwords don't match
            }
             Model.getInstance().getUserProperty().setUsername(userNameField.getText().trim());
             Model.getInstance().getUserProperty().setPassword(passwordField.getText().trim());

            userController.registerUser(Model.getInstance().getUserProperty());

            // Show success message
            Utils.notifyUser("You successfully registered!", "Registration", "Success", Alert.AlertType.INFORMATION);

        }
        // Empty fields
        userNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }

    private void onLoginHandler() {

        confirmPasswordText.setVisible(false);
        confirmPasswordField.setVisible(false);
        loginButtonsWrapper.setTranslateY(-50);
        loginSectionWrapper.setTranslateY(50);


        if (!isRegisterMode.get()) { //

            Model.getInstance().getUserProperty().setUsername(userNameField.getText().trim());
            Model.getInstance().getUserProperty().setPassword(passwordField.getText().trim());

            Model.getInstance().setUserProperty(userController.loginUser(Model.getInstance().getUserProperty()));

            Model.getInstance().getWorksheetProperty().setUserProperty(Model.getInstance().getUserProperty());

          // Model.getInstance().getViewFactory().showLandingWindow();
           Model.getInstance().getViewFactory().showMainWindow();


            // Show success message
            Utils.notifyUser("You successfully logged in!", "Login", "Success", Alert.AlertType.INFORMATION);

            Stage currentStage = (Stage) loginWindowParent.getScene().getWindow();

            // We are  attaching username  for worksheet generation
            Model.getInstance().getWorksheetPropertyForGeneration().setUserProperty(new UserProperty(userNameField.getText().trim()));

            Model.getInstance().getViewFactory().closeStage(currentStage);
        }
        // Empty fields
        userNameField.setText("");
        passwordField.setText("");

        isRegisterMode.set(false);
    }
}
