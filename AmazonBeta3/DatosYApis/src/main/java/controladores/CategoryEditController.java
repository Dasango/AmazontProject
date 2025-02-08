package controladores;

import accesoDatos.CategoryAd;
import data.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class CategoryEditController {

    @FXML
    private TextField searchBar;

    @FXML
    private Button editButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea imagesArea;

    @FXML
    private Label errorLabel;

    private int currentCategoryId = -1; 

    @FXML
    public void initialize() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getCategory();
            }
        });
    }

    @FXML
    public void getCategory() {
        try {
            String search = searchBar.getText().trim();
            searchBar.setText("");

            int categoryId = Integer.parseInt(search);
            
            Category category = new CategoryAd().obtenerPorId(categoryId);

            if (category != null) {
                errorLabel.setText("Editando categoría con ID: " + categoryId);
                nameField.setText(category.name());
                imagesArea.setText(category.image());
                editButton.setDisable(false);
                currentCategoryId = categoryId; 
            } else {
                errorLabel.setText("Categoría no encontrada");
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Por favor, ingrese un número válido :(");
        } catch (Exception e) {
            errorLabel.setText("No se encontró la categoría :(");
        }
    }

    @FXML
    private void handleEditCategory() {
        clearErrors();

        if (currentCategoryId == -1) {
            errorLabel.setText("Debe buscar una categoría antes de editar.");
            return;
        }

        try {
            String name = nameField.getText().trim();
            String image = imagesArea.getText().trim();

            if (name.isEmpty() || image.isEmpty()) {
                errorLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            Category category = new Category(currentCategoryId, name, image);
            boolean updated = new CategoryAd().actualizar(category);

            if (updated) {
                errorLabel.setText("Categoría actualizada correctamente.");
                clearFields();
                currentCategoryId = -1; 
                System.out.println("Categoria editada exitosamente :)  " + category);
            } else {
                errorLabel.setText("No se pudo actualizar la categoría.");
            }
        } catch (Exception e) {
            errorLabel.setText("Error al actualizar la categoría.");
        }
    }

    private void clearFields() {
        nameField.clear();
        imagesArea.clear();
    }

    private void clearErrors() {
        errorLabel.setText("");
    }
}

