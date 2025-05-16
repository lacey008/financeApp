package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class TransactionFormController {
    public enum Mode { ADD, EDIT }

    @FXML private Text formTitle;
    @FXML private DatePicker datePicker;
    @FXML private TextField descriptionField;
    @FXML private TextField amountField;
    @FXML private ComboBox<String> typeComboBox;

    private Mode mode;
    private Transaction transaction;

    @FXML
    public void initialize() {
        typeComboBox.getItems().addAll("INCOME", "EXPENSE");
        typeComboBox.setValue("EXPENSE");

    }

    public void setMode(Mode mode) {
        this.mode = mode;
        formTitle.setText(mode == Mode.ADD ? "Add Transaction" : "Edit Transaction");
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        if (transaction != null) {
            datePicker.setValue(transaction.getDate());
            descriptionField.setText(transaction.getDescription());
            amountField.setText(String.valueOf(transaction.getAmount()));
            typeComboBox.setValue(transaction.getType());
        }
    }

    @FXML
    private void handleSave() {
        try {
            LocalDate date = datePicker.getValue();
            String description = descriptionField.getText();
            double amount = Double.parseDouble(amountField.getText());
            String type = typeComboBox.getValue();

            if (description.isEmpty()) {
                showError("Please enter a description");
                return;
            }

            if (mode == Mode.ADD) {
                transaction = new Transaction(0, date, description, amount, type);
                Data.addTransaction(transaction);
            } else {
                transaction.setDate(date);
                transaction.setDescription(description);
                transaction.setAmount(amount);
                transaction.setType(type);
                Data.updateTransaction(transaction);
            }

            handleBack();
        } catch (NumberFormatException e) {
            showError("Please enter a valid amount");
        }
    }

    @FXML
    private void handleCancel() {
        handleBack();
    }

    @FXML
    private void handleBack() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("TransactionsList.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) formTitle.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
} 