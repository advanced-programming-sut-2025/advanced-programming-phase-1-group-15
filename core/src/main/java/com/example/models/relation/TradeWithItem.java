package com.example.models.relation;

import com.example.models.Player;

public class TradeWithItem extends Trade{
    private String TargetName;
    private int TargetAmount;
    public TradeWithItem(int id, Player seller, Player buyer , String type ,String name,  int amount , String TargetName , int TargetAmount) {
        super(id, seller, buyer, type , name , amount);
        this.TargetName = TargetName;
        this.TargetAmount = TargetAmount;
    }
    public String getTargetName() {
        return TargetName;
    }
    public int getTargetAmount() {
        return TargetAmount;
    }
}
