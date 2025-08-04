package com.example.common;

import com.example.common.enums.Gender;

public class User {
    protected String username;
    protected transient String password;
    protected transient String hashedPassword;
    protected String nickname;
    protected String email;
    protected Gender gender;

    protected String securityQuestion;
    protected String securityQuestionAnswer;

    private String avatarKey;
    protected int maxEarnedGold = 0;
    protected int gameCount = 0;
    protected String lobbyId = null;

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

    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityQuestionAnswer() {
        return securityQuestionAnswer;
    }
    public void setSecurityQuestionAnswer(String securityQuestionAnswer) {
        this.securityQuestionAnswer = securityQuestionAnswer;
    }

    public int getMaxEarnedGold() {
        return maxEarnedGold;
    }
    public void setMaxEarnedGold(int maxEarnedGold) {
        this.maxEarnedGold = maxEarnedGold;
    }

    public int getGameCount() {
        return gameCount;
    }
    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public String getLobbyId() {
        return lobbyId;
    }
    public void setLobbyId(String lobbyId) {
        this.lobbyId = lobbyId;
    }

    public User() {

    }

    public User(String username, String password, String nickname, String email, Gender gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.gender = gender;

        if (gender.equals(Gender.BOY)) {
            avatarKey = "boy_default";
        }
        else {
            avatarKey = "girl_default";
        }
    }

    public String getAvatarKey() {
        return avatarKey;
    }
    public void setAvatarKey(String avatarKey) {
        this.avatarKey = avatarKey;
    }
}

