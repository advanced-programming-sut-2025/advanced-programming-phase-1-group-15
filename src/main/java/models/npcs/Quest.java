package models.npcs;

import models.tools.BackPackable;

public class Quest {
    BackPackable request;
    BackPackable reward;
    int requestAmount;
    int rewardAmount;
    boolean doneBySomeone = false;

    public Quest(BackPackable request, BackPackable reward, int requestAmount, int rewardAmount) {
        this.request = request;
        this.reward = reward;
        this.requestAmount = requestAmount;
        this.rewardAmount = rewardAmount;
    }

    public BackPackable getRequest() {
        return request;
    }

    public BackPackable getReward() {
        return reward;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public int getRequestAmount() {
        return requestAmount;
    }

    public void setDoneBySomeone(boolean doneBySomeone) {
        this.doneBySomeone = doneBySomeone;
    }
}
