package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class TransactionsListController {
    @FXML private TableView<Transaction> transactionsTable;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML private TableColumn<Transaction, String> descriptionColumn;
    @FXML private TableColumn<Transaction, Double> amountColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, Void> actionsColumn;
    @FXML private Button saveButton;

    private List<Transaction> transactions;

    @FXML
    public void initialize() {
        transactions = Data.getAllTransactions();
        setupTableColumns();
        transactionsTable.getItems().setAll(transactions);
    }

    private void setupTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));

        // Add edit and delete buttons to actions column
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    handleEditTransaction(transaction);
                });

                deleteButton.setOnAction(event -> {
                    Transaction transaction = getTableView().getItems().get(getIndex());
                    handleDeleteTransaction(transaction);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttons = new HBox(5, editButton, deleteButton);
                    setGraphic(buttons);
                }
            }
        });
    }

//    public void setTransactions(List<Transaction> transactions) {
//        this.transactions = transactions;
//        if (transactions != null){
//            transactionsTable.getItems().setAll(transactions);
//        }
//    }

    @FXML
    private void handleAddTransaction() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("TransactionForm.fxml"));
            Parent root = loader.load();
            
            TransactionFormController controller = loader.getController();
            controller.setMode(TransactionFormController.Mode.ADD);
            
            Stage stage = (Stage) transactionsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditTransaction(Transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("TransactionForm.fxml"));
            Parent root = loader.load();
            
            TransactionFormController controller = loader.getController();
            controller.setMode(TransactionFormController.Mode.EDIT);
            controller.setTransaction(transaction);
            
            Stage stage = (Stage) transactionsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteTransaction(Transaction transaction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Transaction");
        alert.setContentText("Are you sure you want to delete this transaction?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            Data.deleteTransaction(transaction);
            transactions.remove(transaction);
            transactionsTable.getItems().remove(transaction);
        }
    }

    @FXML
    private void handleBackToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) transactionsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveTransactions() {
        Data.saveTransactions();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Transactions saved successfully!");
        alert.showAndWait();
    }
} 