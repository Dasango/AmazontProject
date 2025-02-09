package controladores;

import java.sql.SQLException;

import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import data.Category;
import data.Product;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import serv.ProductService;

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

		categoryChoiceBox.getItems().setAll(new CategoryAd().obtenerTodos());

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
			String[] imagesArray = images.split("\n");

			// Validar que cada imagen sea una URL
			for (String img : imagesArray) {
				if (!img.matches("https?://.*")) { // Debe comenzar con http:// o https://
					errorLabel.setText("Cada imagen debe ser una URL válida.");
					return;
				}
			}
			if (name.isEmpty() || category == null || description.isEmpty() || images.isEmpty()) {
				errorLabel.setText("Todos los campos son obligatorios.");
				return;
			}

			var product = new Product(1, name, price, description, category, images.split("\n"));

			new ProductAd().crear(product);
			//ProductService.createProduct(product);

			System.out.println("Producto agregado: " + product);

			clearFields();

		} catch (NumberFormatException e) {
			errorLabel.setText("Por favor, ingrese un precio válido.");
		} catch (SQLException e) {
			errorLabel.setText("Error al agregar el producto.");
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
