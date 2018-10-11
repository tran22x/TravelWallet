package com.example.olive.travelcredit.data;

/**
 * Created by olive on 5/10/18.
 */

public class Transaction {


    public String senderId;
    public String receiverId;
    public int amount;
    public String detail;
    public String name;

    public Transaction () {

    }

    public Transaction (String name, String senderId, String receiverId, int amount, String detail) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.detail = detail;
        this.name = name;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
