package com.example.olive.travelcredit.data;

import java.io.Serializable;

/**
 * Created by minhntran on 5/15/18.
 */

public class Record implements Serializable {
    private String name;
    private int amount;

    public Record(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return this.name;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setName() {
        this.name = name;
    }

    public void setAmount() {
        this.amount = amount;
    }


}
