package controladores;

import data.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CategoryAddController {
	
	@FXML
	private TextField nameField;

	@FXML
	private TextArea imagesArea;

	@FXML
	private Label errorLabel;

	@FXML
	public void initialize() {

	}

	@FXML
	private void handleAddCategory() {
		clearErrors();

		try {
			String name = nameField.getText().trim();

			String image = imagesArea.getText().trim();

			if (name.isEmpty() || image.isEmpty()) {
				errorLabel.setText("Todos los campos son obligatorios.");
				return;
			}

			var category = new Category(1, name, image);

			// Supón que el servicio agrega el producto a una base de datos o una lista.
			// serv.ProductService.addProduct(product);

			System.out.println("Producto agregado: " + category);

			clearFields();

		} catch (NumberFormatException e) {
			errorLabel.setText("Por favor, ingrese un precio válido.");
		} catch (Exception e) {
			errorLabel.setText("Error al agregar el producto.");
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
