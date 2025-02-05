package com.wgapp.worksheetgenerator.Controllers.UI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//@ExtendWith(MockitoExtension.class)
class MainWindowControllerTest extends ApplicationTest {

    private MainWindowController mainWindowController;
    private GeneratorWindowController generatorWindowController;
    private Stage stage; // Ensure stage is stored

    @BeforeEach
    public void setUp() throws Exception {
        // Ensure JavaFX Toolkit is properly started
        FxToolkit.registerPrimaryStage();
        // Setup a new Stage for each test
        stage = FxToolkit.setupStage(stage -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainWindow.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mainWindowController = loader.getController(); // Get controller from FXML

            stage.setScene(scene);
            stage.show();
        });

        // Load GeneratorView.fxml
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Views/GeneratorView.fxml"));
        AnchorPane generatorView = loader2.load();
        generatorWindowController = loader2.getController();


        // Set generator view inside mainWindow's center
        Platform.runLater(() -> mainWindowController.mainWindow.setCenter(generatorView));

        // Ensure JavaFX events are processed
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void check() {
        assertNotNull(mainWindowController.mainWindow.getCenter(), "Center should not be null");
        assertEquals("generatorWindowParent", mainWindowController.mainWindow.getCenter().getId());
    }

    @AfterEach
    public void tearDown() throws Exception {
        FxToolkit.cleanupStages(); // Close all open stages
        Platform.setImplicitExit(false);
    }
}



//class MainWindowControllerTest extends ApplicationTest {
//
//    private MainWindowController mainWindowController;
//    private GeneratorWindowController generatorWindowController;
//
//    @Start
//    public void start(Stage stage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/MainWindow.fxml"));
//        Scene scene = new Scene(loader.load());
//
//        mainWindowController = loader.getController();
//
//        // Load GeneratorView.fxml
//        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/Views/GeneratorView.fxml"));
//        AnchorPane generatorView = loader2.load();
//        generatorWindowController = loader2.getController();
//
//        stage.setScene(scene);
//        stage.show();
//        stage.toFront();
//
//        // Set generator view inside mainWindow's center
//        Platform.runLater(() -> mainWindowController.mainWindow.setCenter(generatorView));
//    }
//
//    @Test
//    public void check() {
//        WaitForAsyncUtils.waitForFxEvents(); // ✅ Ensure JavaFX UI is ready before assertions
//        assertNotNull(mainWindowController.mainWindow.getCenter(), "Center should not be null");
//        assertEquals("generatorWindowParent", mainWindowController.mainWindow.getCenter().getId());
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        FxToolkit.hideStage();
//        Platform.setImplicitExit(false);
//    }
//}
