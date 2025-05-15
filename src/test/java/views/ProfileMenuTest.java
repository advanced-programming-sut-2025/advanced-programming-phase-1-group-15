package views;

import models.App;
import models.User;
import models.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class ProfileMenuTest {

    private Scanner scanner;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User("testUser", "test#1234", "TestNickname", "test@example.com", Gender.BOY);
        App.currentUser = user;
        App.users.add(user);
    }

    @Test
    void testShowProfileMenu() {
        scanner = new Scanner("show current menu\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);
    }

    @Test
    void testSwitchToMainMenu() {
        scanner = new Scanner("menu enter main menu\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);

        assertTrue(AppView.currentMenu instanceof MainMenu);
    }

    @Test
    void testChangeUsername() {
        scanner = new Scanner("change username -u newUser\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);

        assertEquals("newUser", App.currentUser.getUsername());
    }

    @Test
    void testChangeNickname() {
        scanner = new Scanner("change nickname -n newNickname\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);

        assertEquals("newNickname", App.currentUser.getNickname());
    }

    @Test
    void testChangeEmail() {
        scanner = new Scanner("change email -e new@example.com\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);

        assertEquals("new@example.com", App.currentUser.getEmail());
    }

    @Test
    void testChangePassword() {
        scanner = new Scanner("change password -p 1234#newPass -o test#1234\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);

        assertEquals("1234#newPass", App.currentUser.getPassword());
    }

    @Test
    void testUserInfo() {
        scanner = new Scanner("user info\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);
    }

    @Test
    void testInvalidCommand() {
        scanner = new Scanner("invalid command\n");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(scanner);
    }
}