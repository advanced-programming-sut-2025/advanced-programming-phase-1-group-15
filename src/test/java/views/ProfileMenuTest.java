package views;

import controllers.ProfileMenuController;
import models.Result;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Scanner;

import static org.mockito.Mockito.*;

class ProfileMenuTest {

    private Scanner mockScanner;

    @BeforeEach
    void setUp() {
        mockScanner = mock(Scanner.class);
    }

    @Test
    void testShowProfileMenu() {
        when(mockScanner.nextLine()).thenReturn("show current menu");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(mockScanner);
    }

    @Test
    void testSwitchToMainMenu() {
        when(mockScanner.nextLine()).thenReturn("switch menu main menu");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(mockScanner);
    }

    @Test
    void testChangeUsername() {
        try (MockedStatic<ProfileMenuController> mockedController = Mockito.mockStatic(ProfileMenuController.class)) {
            mockedController.when(() -> ProfileMenuController.changeUsername("newUser")).thenReturn(new Result(true, "Username changed successfully!"));

            when(mockScanner.nextLine()).thenReturn("change username newUser");

            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.run(mockScanner);

            mockedController.verify(() -> ProfileMenuController.changeUsername("newUser"), times(1));
        }
    }

    @Test
    void testChangeNickname() {
        try (MockedStatic<ProfileMenuController> mockedController = Mockito.mockStatic(ProfileMenuController.class)) {
            mockedController.when(() -> ProfileMenuController.changeNickname("newNick")).thenReturn(new Result(true, "Nickname changed successfully!"));

            when(mockScanner.nextLine()).thenReturn("change nickname newNick");

            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.run(mockScanner);

            mockedController.verify(() -> ProfileMenuController.changeNickname("newNick"), times(1));
        }
    }

    @Test
    void testChangeEmail() {
        try (MockedStatic<ProfileMenuController> mockedController = Mockito.mockStatic(ProfileMenuController.class)) {
            mockedController.when(() -> ProfileMenuController.changeEmail("new@example.com")).thenReturn(new Result(true, "Email changed successfully!"));

            when(mockScanner.nextLine()).thenReturn("change email new@example.com");

            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.run(mockScanner);

            mockedController.verify(() -> ProfileMenuController.changeEmail("new@example.com"), times(1));
        }
    }

    @Test
    void testChangePassword() {
        try (MockedStatic<ProfileMenuController> mockedController = Mockito.mockStatic(ProfileMenuController.class)) {
            mockedController.when(() -> ProfileMenuController.changePassword("newPass", "oldPass")).thenReturn(new Result(true, "Password changed successfully!"));

            when(mockScanner.nextLine()).thenReturn("change password oldPass newPass");

            ProfileMenu profileMenu = new ProfileMenu();
            profileMenu.run(mockScanner);

            mockedController.verify(() -> ProfileMenuController.changePassword("newPass", "oldPass"), times(1));
        }
    }

//    @Test
//    void testUserInfo() {
//        try (MockedStatic<App> mockedApp = Mockito.mockStatic(App.class)) {
//            User mockUser = mock(User.class);
//            mockedApp.when(() -> App.currentUser).thenReturn(mockUser);
//
//            when(mockScanner.nextLine()).thenReturn("show user info");
//
//            ProfileMenu profileMenu = new ProfileMenu();
//            profileMenu.run(mockScanner);
//        }
//    }

    @Test
    void testInvalidCommand() {
        when(mockScanner.nextLine()).thenReturn("invalid command");

        ProfileMenu profileMenu = new ProfileMenu();
        profileMenu.run(mockScanner);
    }
}
