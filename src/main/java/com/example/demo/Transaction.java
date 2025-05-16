package com.example.demo;

import java.time.LocalDate;

public class Transaction {
    private int id;
    private LocalDate date;
    private String description;
    private double amount;
    private String type; // "INCOME" or "EXPENSE"

    public Transaction(int id, LocalDate date, String description, double amount, String type) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.amount = amount;
        this.type = type;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return description + " - " + amount;
    }
} 