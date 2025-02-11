package controladores;

import java.io.IOException;

import accesoDatos.CategoryAd;
import data.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import serv.CategoryService;

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
			
			var categoryinApi=CategoryService.createCategory(category);

			new CategoryAd().crear(categoryinApi);
			
			
			System.out.println("Categoria agregado: " + category);

			clearFields();

		} catch (NumberFormatException e) {
			errorLabel.setText("Por favor, ingrese un precio válido.");
		} catch (IOException e) {
			errorLabel.setText("Por favor un link valido");

		}catch (Exception e) {
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
