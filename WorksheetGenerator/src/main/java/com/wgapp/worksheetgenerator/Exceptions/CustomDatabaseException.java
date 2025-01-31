package com.wgapp.worksheetgenerator.Exceptions;

import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.scene.control.Alert;

public class CustomDatabaseException extends RuntimeException {

    public CustomDatabaseException(String message) {
        super((message));
        if (message.equals("Your password is incorrect. Please try again.")) {
            Utils.notifyUser(message, "Invalid password.", "Login Error", Alert.AlertType.ERROR);
        } else if (message.equals("Your username is incorrect.")) {

            Utils.notifyUser(message, "Invalid username.", "Login Error", Alert.AlertType.ERROR);
        } else {

            Utils.notifyUser(message, "Database error", "Error", Alert.AlertType.ERROR);
        }
    }


    public CustomDatabaseException(Throwable cause) {
        super(transformMessage(cause));
    }

    private static String transformMessage(Throwable cause) {
        if (cause.getMessage().contains("CK__users__user_pinN")) {
            System.out.println(cause.getMessage());
            Utils.notifyUser("Invalid PIN format! The PIN must be exactly 4 numeric digits.", "Invalid PIN", "PIN Creation Error", Alert.AlertType.ERROR);
            return "Invalid PIN format! The PIN must be exactly 4 numeric digits.";
        }

        if (cause.getMessage().contains("UQ_users_user_name")) {
            Utils.notifyUser("This user is already exists.", "Invalid User Name", "Register Error", Alert.AlertType.ERROR);
            return "This username is already taken. Please choose another one.";
        }
        Utils.notifyUser(cause.getMessage(), "Database error", "Error", Alert.AlertType.ERROR);
        return cause.getMessage(); // Default: keep original message
    }
}
