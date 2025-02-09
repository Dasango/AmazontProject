package controladores;

import java.util.Optional;

import accesoDatos.CategoryAd;
import data.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Button deleteButton;

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
                deleteButton.setDisable(false); // Habilitamos el botón de eliminar
                currentCategoryId = categoryId; 
            } else {
                errorLabel.setText("Categoría no encontrada");
                deleteButton.setDisable(true); // Deshabilitamos el botón si no se encuentra la categoría
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Por favor, ingrese un número válido :(");
        } catch (Exception e) {
            errorLabel.setText("No se encontró la categoría :(");
        }
    }

    @FXML
    public void handleDeleteCategory() {
        try {
            if (currentCategoryId == -1) {
                errorLabel.setText("Debe buscar una categoría antes de eliminar.");
                return;
            }

            // Confirmación de eliminación
            boolean confirm = showConfirmationDialog();  // Llamamos a un método de confirmación (ver abajo)
            if (!confirm) {
                return;
            }

            // Intentamos eliminar la categoría
            boolean deleted = new CategoryAd().eliminar(currentCategoryId);

            if (deleted) {
                errorLabel.setText("Categoría eliminada correctamente.");
                clearFields();
                currentCategoryId = -1;
                deleteButton.setDisable(true); // Deshabilitamos el botón después de la eliminación
            } else {
                errorLabel.setText("No se pudo eliminar la categoría.");
            }
        } catch (Exception e) {
            errorLabel.setText("Error al eliminar la categoría.");
            e.printStackTrace();
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar esta categoría?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
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

