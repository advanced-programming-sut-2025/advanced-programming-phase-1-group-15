package com.example.common.relation;

import com.example.common.Player;

public class Trade {
    private int id;
    private Player seller;
    private Player buyer;
    private String type;
    private String name;
    private int amount;
    private boolean newTrade = true;
    public Trade(int id, Player seller, Player buyer , String type , String name ,  int amount) {
        this.id = id;
        this.seller = seller;
        this.buyer = buyer;
        this.type = type;
        this.name = name;
        this.amount = amount;
    }
    public int getId() {
        return id;
    }
    public Player getSeller() {
        return seller;
    }
    public Player getBuyer() {
        return buyer;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public int getAmount() {
        return amount;
    }
    public boolean isNewTrade() {
        return newTrade;
    }
    public void setNewTrade(boolean newTrade) {
        this.newTrade = newTrade;
    }
}
