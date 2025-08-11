package com.example.common.npcs;

import com.example.common.tools.BackPackable;

public class Quest {
    BackPackable request;
    BackPackable reward;
    int requestAmount;
    int rewardAmount;
    boolean doneBySomeone = false;
    private int completionCount = 0;

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

    public int getCompletionCount() {
        return completionCount;
    }

    public void incrementCompletionCount() {
        this.completionCount++;
    }

    public String getInfo(int number) {
        String completionInfo = completionCount > 0 ? String.format(" [Completed %d times]", completionCount) : "";

        return String.format(
            "%d : Deliver %d × %s → Reward: %d × %s%s\n",
            number,
            requestAmount,
            request.getName(),
            rewardAmount,
            reward.getName(),
            completionInfo
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Quest quest = (Quest) obj;
        return requestAmount == quest.requestAmount &&
            rewardAmount == quest.rewardAmount &&
            request.getName().equals(quest.request.getName()) &&
            reward.getName().equals(quest.reward.getName());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(request.getName(), reward.getName(), requestAmount, rewardAmount);
    }
}
