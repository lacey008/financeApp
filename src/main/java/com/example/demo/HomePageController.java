package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class HomePageController {
    @FXML private Text totalTransactionsText;
    @FXML private Text totalIncomeText;
    @FXML private Text totalExpensesText;

    private List<Transaction> transactions;

    @FXML
    public void initialize() {
        updateSummary();
    }

    private void updateSummary() {
        transactions = Data.getAllTransactions();
        int totalTransactions = transactions.size();
        double totalIncome = Data.getTotalIncome();
        double totalExpenses = Data.getTotalExpenses();

        totalTransactionsText.setText(String.valueOf(totalTransactions));
        totalIncomeText.setText(String.format("$%.2f", totalIncome));
        totalExpensesText.setText(String.format("$%.2f", totalExpenses));
    }

    @FXML
    private void handleViewTransactions() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("TransactionsList.fxml"));
            Parent root = loader.load();
            
//            TransactionsListController controller = loader.getController();
//            controller.setTransactions(transactions);
            
            Stage stage = (Stage) totalTransactionsText.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 