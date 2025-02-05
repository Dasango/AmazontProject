package controladores;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import data.Category;
import data.Product;
import data.User;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.StringConverter;
import serv.CategoryService;

public class ProductAddController {

	@FXML
	private TextField searchBar;

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



	@FXML
	public void initialize() {
	    try {
	        List<Category> allCategories = serv.CategoryService.getAllCategories(); 


	        categoryChoiceBox.getItems().setAll(allCategories);

	        categoryChoiceBox.setConverter(new StringConverter<>() {
	            @Override
	            public String toString(Category category) {
	                return category != null ? category.name() : "";
	            }

	            @Override
	            public Category fromString(String string) {
	                return categoryChoiceBox.getItems().stream()
	                        .filter(category -> category.name().equals(string))
	                        .findFirst()
	                        .orElse(null);
	            }
	        });

	    } catch (IOException e) {
	        System.err.println("Problema con las categorías: " + e.getMessage());
	    }
	}


	@FXML
	private void handleAddProduct() {
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

			// ADRIAN YA LEE EL LO DE LOS CAMPOS SOLO MAQUINALE COMO ENVIARLES A LA BASE
			// AQUI PRUEBO QUE SI CREA UN PRODUCTO
			var product = new Product(1, name, price, description, category, images.split("\n"));

			// AQUI HAS TU CODIGO
			System.out.println("Producto agregado: " + product);

			clearFields();

		} catch (NumberFormatException e) {
			errorLabel.setText("Por favor, ingrese un precio válido.");
		} catch (Exception e) {
			errorLabel.setText("Error al agregar el producto.");
		}
	}

	private void clearFields() {
		nameField.clear();
		priceField.clear();
		categoryChoiceBox.getSelectionModel().clearSelection();
		descriptionArea.clear();
		imagesArea.clear();
	}

	private void clearErrors() {
		errorLabel.setText("");
	}
}
