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
import java.util.List;

public class CategoriesListController {
    @FXML private TableView<Category> categoriesTable;
    @FXML private TableColumn<Category, String> nameColumn;
    @FXML private TableColumn<Category, Double> budgetColumn;
    @FXML private TableColumn<Category, Double> balanceColumn;
    @FXML private TableColumn<Category, Void> actionsColumn;
    @FXML private Button saveButton;

    private List<Category> categories;

    @FXML
    public void initialize() {
        categories = CategoryData.getAllCategories();
        setupTableColumns();
        categoriesTable.getItems().setAll(categories);
    }

    private void setupTableColumns() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        budgetColumn.setCellValueFactory(new PropertyValueFactory<>("budget"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Edit and delete buttons in actions column
        actionsColumn.setCellFactory(col -> new TableCell<>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            {
                editButton.setOnAction(event -> {
                    Category category = getTableView().getItems().get(getIndex());
                    handleEditCategory(category);
                });

                deleteButton.setOnAction(event -> {
                    Category category = getTableView().getItems().get(getIndex());
                    handleDeleteCategory(category);
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

    @FXML
    private void handleAddCategory() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("CategoryForm.fxml"));
            Parent root = loader.load();
            
            CategoryFormController controller = loader.getController();
            controller.setMode(CategoryFormController.Mode.ADD);
            
            Stage stage = (Stage) categoriesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleEditCategory(Category category) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("CategoryForm.fxml"));
            Parent root = loader.load();
            
            CategoryFormController controller = loader.getController();
            controller.setMode(CategoryFormController.Mode.EDIT);
            controller.setCategory(category);
            
            Stage stage = (Stage) categoriesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDeleteCategory(Category category) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Delete");
        alert.setHeaderText("Delete Category");
        alert.setContentText("Are you sure you want to delete this category?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            CategoryData.deleteCategory(category);
            categories.remove(category);
            categoriesTable.getItems().remove(category);
        }
    }

    @FXML
    private void handleBackToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("HomePage.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) categoriesTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSaveCategories() {
        CategoryData.saveCategories();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("Categories saved successfully!");
        alert.showAndWait();
    }

}