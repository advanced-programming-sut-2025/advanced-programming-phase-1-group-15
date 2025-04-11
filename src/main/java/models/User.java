package models;

import models.enums.Gender;

import java.util.ArrayList;

public class User {
    protected String username;
    protected String password;
    protected String nickname;
    protected String email;
    protected Gender gender;

    protected ArrayList<String> securityQuestions = new ArrayList<>();

    protected int maxEarnedCoins;
    protected int gameCount;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public ArrayList<String> getSecurityQuestions() {
        return securityQuestions;
    }
    public void setSecurityQuestions(ArrayList<String> securityQuestions) {
        this.securityQuestions = securityQuestions;
    }

    public User() {

    }

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;
    }

    public void userInfo() {

    }
}

