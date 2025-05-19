package com.example.demo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryData {
    private static List<Category> categories = new ArrayList<>();
    private static int nextId = 1;
    private static final String CSV_FILE = "src/data/categories.csv";

    // Load categories from CSV file
    public static void loadTransactions() {
        categories.clear();
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
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double budget = Double.parseDouble(parts[2]);
                    double balance = Double.parseDouble(parts[3]);
                    
                    categories.add(new Category(id, name, budget, balance));
                    nextId = Math.max(nextId, id + 1);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save categories to CSV file
    public static void saveCategories() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CSV_FILE))) {
            // Write header
            writer.println("ID,Name,Budget,Balance");
            
            // Write transactions
            for (Category c : categories) {
                writer.printf("%d,%s,%.2f,%.2fn",
                    c.getId(),
                    c.getName(),
                    c.getBudget(),
                    c.getBalance());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all categories
    public static List<Category> getAllCategories() {
        return new ArrayList<>(categories);
    }

    // Add a new category
    public static void addCategory(Category category) {
        category.setId(nextId++);
        categories.add(category);
    }

    // Update an existing category
    public static void updateCategory(Category updatedCategory) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId() == updatedCategory.getId()) {
                categories.set(i, updatedCategory);
                break;
            }
        }
    }

    // Delete a category
    public static void deleteCategory(Category category) {
        categories.removeIf(c -> c.getId() == category.getId());
    }
}
