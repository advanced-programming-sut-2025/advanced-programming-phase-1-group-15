package com.example.models.npcs;

import com.example.models.tools.BackPackable;

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

    public boolean isDoneBySomeone() {
        return doneBySomeone;
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

    public String getInfo(int number) {
        return String.format(
                "%d : Deliver %d × %s → Reward: %d × %s\n",
                number,
                requestAmount,
                request.getName(),
                rewardAmount,
                reward.getName()
        );
    }


}
