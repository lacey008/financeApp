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

public class CategoryFormController {
    public enum Mode { ADD, EDIT }

    @FXML private Text formTitle;
    @FXML private TextField nameField;
    @FXML private TextField budgetField;

    private Mode mode;
    private Category category;

    public void setMode(Mode mode) {
        this.mode = mode;
        formTitle.setText(mode == Mode.ADD ? "Add Transaction" : "Edit Transaction");
    }

    public void setCategory(Category category) {
        this.category = category;
        if (category != null) {
            nameField.setText(category.getName());
            budgetField.setText(String.valueOf(category.getBudget()));
        }
    }

    @FXML
    private void handleSave() {
        try {
            String name = nameField.getText();
            double budget = Double.parseDouble(budgetField.getText());

            if (name.isEmpty()) {
                showError("Please enter a name");
                return;
            }

            if (budget <= 0) {
                showError("Budget must be positive");
                return;
            }

            if (mode == Mode.ADD) {
                category = new Category(0, name, budget);
                CategoryData.addCategory(category);
            } else {
                category.setName(name);
                category.setBudget(budget);
                category.setBalance(budget);
                CategoryData.updateCategory(category);
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
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("CategoriesList.fxml"));
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
