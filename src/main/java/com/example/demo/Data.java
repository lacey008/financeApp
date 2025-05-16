package com.example.demo;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {
    private static List<Transaction> transactions = new ArrayList<>();
    private static int nextId = 1;
    private static final String CSV_FILE = "src/data/transactions.csv";

    // Load transactions from CSV file
    public static void loadTransactions() {
        transactions.clear();
        File file = new File(CSV_FILE);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            // Skip header
            reader.readLine();
            
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    LocalDate date = LocalDate.parse(parts[1]);
                    String description = parts[2];
                    double amount = Double.parseDouble(parts[3]);
                    String type = parts[4];
                    
                    transactions.add(new Transaction(id, date, description, amount, type));
                    nextId = Math.max(nextId, id + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save transactions to CSV file
    public static void saveTransactions() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            // Write header
            writer.println("ID,Date,Description,Amount,Type");
            
            // Write transactions
            for (Transaction t : transactions) {
                writer.printf("%d,%s,%s,%.2f,%s%n",
                    t.getId(),
                    t.getDate(),
                    t.getDescription(),
                    t.getAmount(),
                    t.getType());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all transactions
    public static List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    // Add a new transaction
    public static void addTransaction(Transaction transaction) {
        transaction.setId(nextId++);
        transactions.add(transaction);
    }

    // Update an existing transaction
    public static void updateTransaction(Transaction updatedTransaction) {
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == updatedTransaction.getId()) {
                transactions.set(i, updatedTransaction);
                break;
            }
        }
    }

    // Delete a transaction
    public static void deleteTransaction(Transaction transaction) {
        transactions.removeIf(t -> t.getId() == transaction.getId());
    }

    // Get total income
    public static double getTotalIncome() {
        return transactions.stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }

    // Get total expenses
    public static double getTotalExpenses() {
        return transactions.stream()
                .filter(t -> "EXPENSE".equals(t.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();
    }
}