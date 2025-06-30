package com.example.views;

import com.example.models.App;
import com.example.models.User;
import com.example.models.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class LoginMenuTest {

    private Scanner scanner;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "test#1234", "test", "test@gmail.com", Gender.BOY);
        user.setSecurityQuestion("What is your favorite food?");
        user.setSecurityQuestionAnswer("correctAnswer");

        App.users.add(user);
    }

    @Test
    void testUsernameMenu_SuggestsNewUsername() {
        scanner = new Scanner("y\n");
        String suggestedUsername = LoginMenu.usernameMenu(scanner, "testUser");

        assertNotNull(suggestedUsername);
        assertTrue(suggestedUsername.startsWith("testUser"));
    }

    @Test
    void testUsernameMenu_UserDeclinesNewUsername() {
        scanner = new Scanner("n\n");
        String suggestedUsername = LoginMenu.usernameMenu(scanner, "testUser");

        assertNull(suggestedUsername);
    }

    @Test
    void testPasswordMenu_GeneratesStrongPassword() {
        scanner = new Scanner("y\n");
        String password = LoginMenu.passwordMenu(scanner);

        assertNotNull(password);
        assertFalse(password.isEmpty());
        assertTrue(password.matches(".*[A-Z].*"), "Should contain an uppercase letter");
        assertTrue(password.matches(".*[a-z].*"), "Should contain a lowercase letter");
        assertTrue(password.matches(".*\\d.*"), "Should contain a digit");
        assertTrue(password.matches(".*[!@#$%^&*()].*"), "Should contain a special character");
    }

    @Test
    void testPasswordMenu_UserDeclinesNewPassword() {
        scanner = new Scanner("n\n");
        String password = LoginMenu.passwordMenu(scanner);

        assertNull(password);
    }

    @Test
    void testForgetPasswordMenu_CorrectAnswerChangesPassword() {
        scanner = new Scanner("correctAnswer\nNewStrongPassword!");

        LoginMenu.forgetPasswordMenu(scanner, "testUser");

        assertEquals("NewStrongPassword!", user.getPassword(), "Password should be updated correctly");
    }

    @Test
    void testForgetPasswordMenu_WrongAnswerFails() {
        scanner = new Scanner("wrongAnswer\n");

        LoginMenu.forgetPasswordMenu(scanner, "testUser");

        assertNotEquals("NewStrongPassword!", user.getPassword(), "Password should not be changed if the answer is wrong");
    }
}