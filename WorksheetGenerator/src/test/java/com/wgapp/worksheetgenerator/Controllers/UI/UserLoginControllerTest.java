package com.wgapp.worksheetgenerator.Controllers.UI;

import com.wgapp.worksheetgenerator.Controllers.UserController;
import com.wgapp.worksheetgenerator.ModelsUI.UserProperty;
import com.wgapp.worksheetgenerator.Utils.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import java.lang.reflect.Field;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserLoginControllerTest extends ApplicationTest {

    UserController mockUserController = mock(UserController.class);
    private UserLoginController userLoginController;
    private Stage stage;
    MockedStatic<Utils> mockedUtils;


    @Start
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/UserLoginWindow.fxml"));
        Scene scene = new Scene(loader.load());
        userLoginController = loader.getController();
        stage.setScene(scene);
        stage.show();
        // this.stage = stage;  // Store reference to stage for later use
    }

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        assertNotNull(userLoginController, "UserLoginController should not be null");
        assertNotNull(userLoginController.loginBtn, "loginBtn should not be null");

        // Use reflection to access the private final field and inject the mock controller
        Field userControllerField = UserLoginController.class.getDeclaredField("userController");
        userControllerField.setAccessible(true); // Make the field accessible
        userControllerField.set(userLoginController, mockUserController); // Set the mock instance

        mockedUtils = Mockito.mockStatic(Utils.class);
        // Clear fields before each test
        interact(() -> {
            userLoginController.userNameField.clear();
            userLoginController.passwordField.clear();
        });

    }

    @AfterEach
    public void tearDown() {
        // Wait briefly to allow transition to be visible before closing
        try {
            Thread.sleep(3000); // Wait for 3 second (to observe transition)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Close the stage after each test to clean up
        if (stage != null) {
            stage.close();
        }
        mockedUtils.close();
    }


    @Test
    public void testLoginMode() {

        // Assert login button is initially disabled
        assert userLoginController.loginBtn.isDisable();

        // Fill in fields using interact()
        interact(() -> {
            userLoginController.userNameField.setText("testUser");
            userLoginController.passwordField.setText("testPassword");
        });

        assertEquals("testUser", userLoginController.userNameField.getText());
        assertEquals("testPassword", userLoginController.passwordField.getText());

        // Assert login button is enabled
        assert !userLoginController.loginBtn.isDisable();

        assertEquals(false, userLoginController.isRegisterMode.get());

        // Prepare the user property to pass to the loginUser method
        UserProperty user = new UserProperty("testUser", "testPassword");
        interact(() -> userLoginController.loginBtn.fire());

        // Call the method on the mock
        mockUserController.loginUser(user);
        verify(mockUserController, times(1)).loginUser(user);

    }

    @Test
    public void testRegisterMode() {

            //   mockedUtils.when(() -> Utils.notifyUser("You successfully registered!", "Registration", "Success", Alert.AlertType.INFORMATION)).thenReturn(new SimpleBooleanProperty(true));

            // Set up your test and interactions
            interact(() -> userLoginController.registerBtn.fire());

            assertEquals(true, userLoginController.isRegisterMode.get());

            assertThat(userLoginController.confirmPasswordField.isVisible()).isTrue();
            assertThat(userLoginController.confirmPasswordText.isVisible()).isTrue();

            interact(() -> {
                userLoginController.userNameField.setText("newUser");
                userLoginController.passwordField.setText("newPass");
                userLoginController.confirmPasswordField.setText("newPass");
            });

            UserProperty user = new UserProperty("newUser", "newPass", "newPass");

            assertEquals("newUser", userLoginController.userNameField.getText());
            assertEquals("newPass", userLoginController.passwordField.getText());
            assertEquals("newPass", userLoginController.confirmPasswordField.getText());

            interact(() -> userLoginController.registerBtn.fire());

            mockUserController.registerUser(user);
            verify(mockUserController, times(1)).registerUser(user);


    }
}





//        userNameField = lookup("#userNameField").queryAs(TextField.class);
//        passwordField = lookup("#passwordField").queryAs(PasswordField.class);
//        loginButton = lookup("#loginBtn").queryAs(Button.class);
//        registerBtn = lookup("#registerBtn").queryAs(Button.class);

//
//
//    @BeforeEach
//    public void setup() {
//        // Mock Model singleton
//        Model mockModel = Mockito.mock(Model.class);
//        UserProperty mockUserProperty = new UserProperty();
//        Mockito.when(mockModel.getUserProperty()).thenReturn(mockUserProperty);
//        Model.setInstance(mockModel);
//    }


//            mockedUtils.verify(() -> Utils.notifyUser(
//                    "You successfully registered!",
//                    "Registration",
//                    "Success",
//                    Alert.AlertType.INFORMATION
//            ), times(1));













