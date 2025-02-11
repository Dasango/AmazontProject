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
import serv.CategoryService;

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
                deleteButton.setDisable(false); 
                currentCategoryId = categoryId; 
            } else {
                errorLabel.setText("Categoría no encontrada");
                deleteButton.setDisable(true); 
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

            boolean confirm = showConfirmationDialog(); 
            if (!confirm) {
                return;
            }
            
            CategoryService.deleteCategory(currentCategoryId); 
            boolean deleted = new CategoryAd().eliminar(currentCategoryId);

            if (deleted) {
                errorLabel.setText("Categoría eliminada correctamente.");
                clearFields();
                currentCategoryId = -1;
                deleteButton.setDisable(true); 
            } else {
                errorLabel.setText("No se pudo eliminar la categoría en la base.");
            }
        } catch (Exception e) {
            errorLabel.setText("No se puede borrar categoría asociado a un producto");
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
            
            var categoryInApi= CategoryService.updateProduct(currentCategoryId, category); 
            
            boolean updated = new CategoryAd().actualizar(categoryInApi);
            
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

