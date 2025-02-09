package controladores;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import data.Category;
import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import serv.CategoryService;

public class ProductEditController {

    @FXML
    private TextField searchBar;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<Category> categoryChoiceBox;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private TextArea imagesArea;

    @FXML
    private Label errorLabel;

    private int idProductEditing;

    @FXML
    public void initialize() {

        // Llenamos el ComboBox con las categorías disponibles
        categoryChoiceBox.getItems().setAll(new CategoryAd().obtenerTodos());

        // Configuración del StringConverter para mostrar las categorías de manera legible
        categoryChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Category category) {
                return category != null ? category.name() : "";
            }

            @Override
            public Category fromString(String string) {
                return categoryChoiceBox.getItems().stream().filter(category -> category.name().equals(string))
                        .findFirst().orElse(null);
            }
        });

        // Manejo del evento al presionar "Enter" en el campo de búsqueda
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getProduct();
            }
        });
    }

    @FXML
    public void getProduct() {

        try {
            var search = searchBar.getText().trim();
            searchBar.setText("");

            int productId = Integer.parseInt(search);

            var product = new ProductAd().obtenerPorId(productId);

            if (product != null) {

                idProductEditing = productId;

                errorLabel.setText("Editando producto con id: " + idProductEditing);

                imagesArea.setText(Arrays.stream(product.images()).collect(Collectors.joining("\n")));
                nameField.setText(product.title());
                priceField.setText(String.valueOf(product.price()));
                categoryChoiceBox.setValue(product.category());
                descriptionArea.setText(product.description());

                // Habilitar los botones si se encuentra el producto
                editButton.setDisable(false);
                deleteButton.setDisable(false);  // Habilitar el botón de eliminar

            } else {
                errorLabel.setText("Producto no encontrado");
                deleteButton.setDisable(true);  // Deshabilitar el botón si no se encuentra el producto
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("Por favor, ingrese un número válido :(");
            deleteButton.setDisable(true);  // Deshabilitar el botón en caso de error
        } catch (Exception e) {
            errorLabel.setText("No se encontró el producto :(");
            deleteButton.setDisable(true);  // Deshabilitar el botón en caso de error
        }
    }

    @FXML
    private void handleDeleteProduct() {
        try {
            if (idProductEditing == 0) {  // Verificar si el producto no ha sido seleccionado aún
                errorLabel.setText("Debe buscar un producto primero para eliminar.");
                return;
            }

            // Verificar si el producto existe con el idProductEditing
            var product = new ProductAd().obtenerPorId(idProductEditing);
            if (product == null) {
                errorLabel.setText("Producto no encontrado.");
                return;
            }

            // Confirmación de eliminación
            boolean confirm = showConfirmationDialog();
            if (!confirm) {
                return; // Si el usuario no confirma, no se elimina
            }

            // Intentamos eliminar el producto
            boolean deleted = new ProductAd().eliminar(idProductEditing);

            if (deleted) {
                errorLabel.setText("Producto eliminado correctamente.");
                clearFields();
            } else {
                errorLabel.setText("No se pudo eliminar el producto.");
            }

        } catch (Exception e) {
            errorLabel.setText("Error al eliminar el producto.");
            e.printStackTrace();
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar este producto?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void handleEditProduct() {
        clearErrors();

        try {
            String name = nameField.getText().trim();
            double price = Double.parseDouble(priceField.getText().trim());
            Category category = categoryChoiceBox.getValue();
            String description = descriptionArea.getText().trim();
            String images = imagesArea.getText().trim();

            if (name.isEmpty() || category == null || description.isEmpty() || images.isEmpty()) {
                errorLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            Product product = new Product(idProductEditing, name, price, description, category, images.split("\n"));
            boolean updated = new ProductAd().actualizar(product);

            if (updated) {
                errorLabel.setText("Producto actualizado correctamente.");
                clearFields();
                System.out.println("Producto actualizado: " + product);

            } else {
                errorLabel.setText("Error al actualizar el producto.");
            }

        } catch (NumberFormatException e) {
            errorLabel.setText("Por favor, ingrese un precio válido.");
        } catch (Exception e) {
            errorLabel.setText("Error al actualizar el producto.");
        }
    }

    private void clearFields() {
        nameField.clear();
        priceField.clear();
        categoryChoiceBox.getSelectionModel().clearSelection();
        descriptionArea.clear();
        imagesArea.clear();
        clearErrors();
        editButton.setDisable(true);
        deleteButton.setDisable(true);  // Deshabilitar el botón de eliminar cuando se borren los campos
    }

    private void clearErrors() {
        errorLabel.setText("");
    }
}
