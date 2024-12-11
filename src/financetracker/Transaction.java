/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financetracker;

/**
 *
 * @author DELL
 */
public class Transaction {
    private String date;
    private String category;
    private int amount;
    private String type;

    // Constructor
    public Transaction(String date, String category, int amount, String type) {
        this.date = date;
        this.category = category;
        this.amount = amount;
        this.type = type;
    }

    // Getters
    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public int getAmount() {
        return amount;
    }

    public String getType() {
        return type;
    }
}

