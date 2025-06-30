package com.example.models.relation;

import com.example.models.Player;

public class TradeWhitMoney extends Trade {
    private int money;
    public TradeWhitMoney(int id, Player seller, Player buyer, String type ,String name , int amount , int money) {
        super(id, seller, buyer, type , name , amount);
        this.money = money;
    }
    public int getMoney() {
        return money;
    }
}
