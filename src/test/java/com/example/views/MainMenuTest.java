package com.example.views;

import com.example.models.App;
import com.example.models.User;
import com.example.models.enums.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainMenuTest {

    private Scanner scanner;
    private User user1, user2, user3;

    @BeforeEach
    void setUp() {
        user1 = new User("user1", "test#1234", "TestNickname", "test@example.com", Gender.BOY);
        user2 = new User("user2", "test#1234", "TestNickname", "test@example.com", Gender.BOY);
        user3 = new User("user3", "test#1234", "TestNickname", "test@example.com", Gender.BOY);
        App.users.add(user1);
        App.users.add(user2);
        App.users.add(user3);
        App.currentUser = user1;
    }

    @Test
    void testShowMainMenu() {
        scanner = new Scanner("show current menu\n");

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(scanner);
    }

    @Test
    void testLogout() {
        scanner = new Scanner("user logout\n");

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(scanner);

        assertNull(App.currentUser);
        assertTrue(AppView.currentMenu instanceof LoginMenu);
    }

    @Test
    void testSwitchToProfileMenu() {
        scanner = new Scanner("menu enter profile menu\n");

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(scanner);

        assertTrue(AppView.currentMenu instanceof ProfileMenu);
    }

    @Test
    void testStartNewGame() {
        scanner = new Scanner("game new -u user2 user3\ngame map 1\ngame map 2\ngame map 3\n");

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(scanner);

        assertNotNull(App.currentGame);
        assertEquals(3, App.currentGame.getPlayers().size());


        assertEquals(1, App.currentGame.getPlayers().get(0).getMapNumber());
        assertEquals(2, App.currentGame.getPlayers().get(1).getMapNumber());
        assertEquals(3, App.currentGame.getPlayers().get(2).getMapNumber());
    }

    @Test
    void testInvalidCommand() {
        scanner = new Scanner("invalid command\n");

        MainMenu mainMenu = new MainMenu();
        mainMenu.run(scanner);
    }
}
