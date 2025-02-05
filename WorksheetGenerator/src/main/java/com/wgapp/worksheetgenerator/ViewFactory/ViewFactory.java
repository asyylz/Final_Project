package com.wgapp.worksheetgenerator.ViewFactory;

import com.wgapp.worksheetgenerator.Controllers.UI.ModalWindowController;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ViewFactory {
    private final Map<UserMenuOptions, Node> viewCache = new HashMap<>();
    private final ObjectProperty<UserMenuOptions> userMenuItemView;

    /*================================= VIEW METHODS ===================================== */

    public ViewFactory() {
        this.userMenuItemView = new SimpleObjectProperty<>();
    }

    public ObjectProperty<UserMenuOptions> getUserSelectMenuView() {
        return userMenuItemView;
    }

    public Node getView(UserMenuOptions option) {
        return viewCache.computeIfAbsent(option, this::loadView);
    }

    private Node loadView(UserMenuOptions option) {
        return switch (option) {
            case GENERATOR -> loadFXML("/Views/GeneratorView.fxml");
            case WORKSHEET -> loadFXML("/Views/WorksheetWithPassageView.fxml");
            case SETTINGS -> loadFXML("/Views/AccountSettingsView.fxml");
            default -> loadFXML("/Views/GeneratorView.fxml");
        };

    }

    private Node loadFXML(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));

            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return new Label("Error loading view: " + fxml);
        }
    }



    public void showMainWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainWindow.fxml"));
        //MainWindowController controller = new MainWindowController();
        //loader.setController(controller);
        createStage(loader, 1200, 920, "Main", true);
    }


    public void showModalWindow(String warningText) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/ModalWindow.fxml"));

        try {
            // Load the FXML and get the controller
            Scene scene = new Scene(loader.load());
            ModalWindowController controller = loader.getController();

            // Pass the warning text to the controller
            controller.setMessageText(warningText);

            // Create and show the modal window
            createStageForModalWindow(scene, 400, 200, "Warning");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showLandingWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/LandingWindow.fxml"));
        createStage(loader, 500, 1010, "Welcome Worksheet Generator Application");

    }

    public void showPassageWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/PassageWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/PassageWindow.css").toExternalForm();
        createStage(loader, stylesheetPath, 900, 700, "Reading Passage", true);
    }


    public void showLoginWindow() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UserLoginWindow.fxml"));
        String stylesheetPath = getClass().getResource("/Styles/UserLoginWindow.css").toExternalForm();
        createStage(loader, stylesheetPath, 600, 400, "LOGIN/REGISTER", false);
    }

    /*================================= STAGE METHODS ===================================== */
    private void createStage(FXMLLoader loader, String stylesheetPath, int width, int height, String title, Boolean isResizable) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
            scene.getStylesheets().add(stylesheetPath);  // Add stylesheet to the scene here
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(isResizable);
        stage.show();
    }

    private void createStage(FXMLLoader loader, int width, int height, String stageTitle) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());

        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();

        // This prevents the window from closing via x button
        stage.setOnCloseRequest(event -> {
        });

        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(false);
        stage.show();
    }

    private void createStageForModalWindow(Scene scene, int width, int height, String stageTitle) {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);  // Blocks interaction with other windows
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        stage.showAndWait();

    }

    private void createStage(FXMLLoader loader, int width, int height, String stageTitle, Boolean isResizable) {
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(stageTitle);
        stage.setMinWidth(width);
        stage.setMinHeight(height);
        stage.setResizable(isResizable);
        stage.show();

    }

    public void closeStage(Stage stage) {
        FadeTransition fadeOut = new FadeTransition();
        fadeOut.setDuration(Duration.millis(200)); // Set duration (500ms for smooth fade)
        fadeOut.setNode(stage.getScene().getRoot()); // Apply the transition to the root node
        fadeOut.setFromValue(1.0); // Start fully visible
        fadeOut.setToValue(0); // End fully invisible
        // Set an event after the transition finishes to close the stage
        fadeOut.setOnFinished(event -> stage.close());
        // Play the transition
        fadeOut.play();
        stage.close();
    }

}
