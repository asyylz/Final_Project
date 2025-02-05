package com.wgapp.worksheetgenerator.Controllers.UI;

//--add-opens=javafx.graphics/com.sun.javafx.application=ALL-UNNAMED

import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.UserProperty;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import com.wgapp.worksheetgenerator.Controllers.UserController;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserLoginControllerTestdeneme extends ApplicationTest {
    @Mock
    private UserController mockUserController;

    public UserLoginController userLoginController;

    @BeforeEach
    void setUp() throws IOException {
        Platform.runLater(() -> {
            try {
                // Mock Model singleton
                Model mockModel = Mockito.mock(Model.class);
                UserProperty mockUserProperty = new UserProperty();
                Mockito.when(mockModel.getUserProperty()).thenReturn(mockUserProperty);
                Model.setInstance(mockModel);

                // Load the FXML and set the controller
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UserLoginWindow.fxml"));  // Corrected path
                loader.setController(userLoginController); // Explicitly set controller
                Parent root = loader.load();

                // Now, the loginBtn should be injected
                userLoginController = loader.getController();


                // Initialize the scene for testing
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    }


}