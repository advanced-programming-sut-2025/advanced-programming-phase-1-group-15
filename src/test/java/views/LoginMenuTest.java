package views;

import models.App;
import models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginMenuTest {

    private Scanner mockScanner;
    private User mockUser;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
        mockUser = mock(User.class);
    }

    @Test
    void testUsernameMenu_SuggestsNewUsername() {
        when(mockScanner.nextLine()).thenReturn("y");
        String suggestedUsername = LoginMenu.usernameMenu(mockScanner, "testUser");

        assertNotNull(suggestedUsername);
        assertTrue(suggestedUsername.startsWith("testUser"));
    }

    @Test
    void testUsernameMenu_UserDeclinesNewUsername() {
        when(mockScanner.nextLine()).thenReturn("n");
        String suggestedUsername = LoginMenu.usernameMenu(mockScanner, "testUser");

        assertNull(suggestedUsername);
    }

    @Test
    void testPasswordMenu_GeneratesStrongPassword() {
        when(mockScanner.nextLine()).thenReturn("y");
        String password = LoginMenu.passwordMenu(mockScanner);

        assertNotNull(password);
        assertTrue(password.length() >= 8, "Password should be at least 8 characters long");
        assertTrue(password.matches(".*[A-Z].*"), "Password should contain at least one uppercase letter");
        assertTrue(password.matches(".*[a-z].*"), "Password should contain at least one lowercase letter");
        assertTrue(password.matches(".*[0-9].*"), "Password should contain at least one digit");
        assertTrue(password.matches(".*[!@#$%^&*()_+\\-=;':\"\\\\|,.<>/?].*"));
    }

    @Test
    void testPasswordMenu_UserDeclinesNewPassword() {
        when(mockScanner.nextLine()).thenReturn("n");
        String password = LoginMenu.passwordMenu(mockScanner);

        assertNull(password);
    }

    @Test
    void testForgetPasswordMenu_CorrectAnswerChangesPassword() {
        try (MockedStatic<App> mockedApp = Mockito.mockStatic(App.class)) {
            mockedApp.when(() -> App.getUserByUsername("testUser")).thenReturn(mockUser);

            when(mockUser.getSecurityQuestion()).thenReturn("What is your favourite food?");
            when(mockUser.getSecurityQuestionAnswer()).thenReturn("correctAnswer");

            when(mockScanner.nextLine()).thenReturn("correctAnswer".trim());
            when(mockScanner.nextLine()).thenReturn("NewStrongPassword!");

            LoginMenu.forgetPasswordMenu(mockScanner, "testUser");

            verify(mockUser, atLeastOnce()).getSecurityQuestion();
            verify(mockUser, atLeastOnce()).getSecurityQuestionAnswer();
            verify(mockUser).setPassword("NewStrongPassword!");
        }
    }

    @Test
    void testForgetPasswordMenu_WrongAnswerFails() {
        when(mockUser.getSecurityQuestionAnswer()).thenReturn("correctAnswer");
        when(mockScanner.nextLine()).thenReturn("wrongAnswer");

        LoginMenu.forgetPasswordMenu(mockScanner, mockUser.getUsername());

        verify(mockUser, times(1)).getSecurityQuestionAnswer();
    }
}
