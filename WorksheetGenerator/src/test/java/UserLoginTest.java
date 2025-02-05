import com.wgapp.worksheetgenerator.Controllers.UI.UserLoginController;
import com.wgapp.worksheetgenerator.Controllers.UserController;
import com.wgapp.worksheetgenerator.ModelsUI.Model;
import com.wgapp.worksheetgenerator.ModelsUI.UserProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class UserLoginTest extends ApplicationTest {

    @Mock
    private UserController mockUserController;

    public UserLoginController userLoginController;

    @Override
    public void start(Stage stage) throws Exception {
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
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void testLoginButton_DisabledWhenFieldsEmpty() {
        // Initial state - fields should be empty and login button disabled
        System.out.println(userLoginController); // This should not be null
        System.out.println(userLoginController.loginBtn); // This should not be null

        // Assert button is disabled initially (assuming it's disabled in FXML)
        assertThat(userLoginController.loginBtn.isDisable()).isTrue();

        // Now simulate entering data into the username and password fields
        clickOn("#userNameField").write("testUser");
        clickOn("#passwordField").write("testPass");

        // Button should be enabled now
        assertThat(userLoginController.loginBtn.isDisable()).isFalse();
    }
}

