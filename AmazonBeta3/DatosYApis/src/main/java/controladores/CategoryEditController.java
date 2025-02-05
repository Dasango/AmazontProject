package controladores;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import data.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
	private TextField nameField;

	@FXML
	private TextArea imagesArea;

	@FXML
	private Label errorLabel;



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
	        var search = searchBar.getText().trim();
	        searchBar.setText(""); 

	        int categoryId = Integer.parseInt(search); 
	        var category = serv.CategoryService.getCategoryById(categoryId); 

	        if (category != null) {
	            errorLabel.setText("Editando categoría con ID: " + categoryId);

	            nameField.setText(category.name());
	            
	            imagesArea.setText(category.image());
	            editButton.setDisable(false); 

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
