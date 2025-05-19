package com.example.demo;

public class Category {
    private int id;
    private String name;
    private double budget;
    private double balance;

    // Constructor for new categories
    // Balance is set by the budget
    public Category(int id, String name, double budget) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.balance = budget;
    }

    // Constructor if the balance is known
    public Category(int id, String name, double budget, double balance) {
        this.id = id;
        this.name = name;
        this.budget = budget;
        this.balance = balance;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return name + " - " + "Budget: " + budget + " , Balance: " + balance;
    }
}
