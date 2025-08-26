package com.example.geektrust.model;

public class MetroCard {
    private final String cardNumber;
    private int balance;

    public MetroCard(String cardNumber, int balance) {
        this.cardNumber = cardNumber;
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getBalance() {
        return balance;
    }

    public boolean deduct(int amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void recharge(int amount) {
        this.balance += amount;
    }
}
