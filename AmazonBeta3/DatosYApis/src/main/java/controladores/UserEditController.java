package controladores;

import data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class UserEditController {
	@FXML
	private TextField searchBar;
	@FXML
	private Button editButton;
	@FXML
	private TextField nameField;

	@FXML
	private TextField emailField;

	@FXML
	private TextField claveField;

	@FXML
	private TextField roleField;

	@FXML
	private TextField avatarField;

	@FXML
	private Label errorLabel;
	

	@FXML
	public void initialize() {
		   searchBar.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ENTER) {
	            	getUser();
	            }
	        });
	}

	@FXML
	public void getUser() {
	    try {
	        var search = searchBar.getText().trim();
	        searchBar.setText(""); // Limpia la barra de búsqueda

	        int userId = Integer.parseInt(search); // Convierte la entrada a un número

	        var user = serv.UserService.getUserById(userId); // Llama al servicio para obtener el usuario

	        if (user != null) {
	            errorLabel.setText("Editando usuario con ID: " + userId);

	            nameField.setText(user.name());
	            emailField.setText(user.email());
	            claveField.setText(user.password());
	            roleField.setText(user.role());
	            avatarField.setText(user.avatar());

	            editButton.setDisable(false); // Habilita el botón de edición

	        } else {
	            errorLabel.setText("Usuario no encontrado");
	        }
	    } catch (NumberFormatException e) {
	        errorLabel.setText("Por favor, ingrese un número válido :(");
	    } catch (Exception e) {
	        errorLabel.setText("No se encontró el usuario :(");
	    }
	}
	
	@FXML
	private void handleAddUser() {
		clearErrors();

		try {
			String name = nameField.getText().trim();
			String email = emailField.getText().trim();
			String clave = claveField.getText().trim();
			String role = roleField.getText().trim();
			String avatar = avatarField.getText().trim();

			// Validar que no haya campos vacíos
			if (name.isEmpty() || email.isEmpty() || clave.isEmpty() || role.isEmpty() || avatar.isEmpty()) {
				errorLabel.setText("Todos los campos son obligatorios.");
				return;
			}

			// Aquí se crearían los objetos de usuario y se los agregarían a una base de
			// datos
			var user = new User(1, name, email, clave, role, avatar);

			// Suponiendo que el servicio agrega el usuario a una base de datos
			// serv.UserService.addUser(user);

			System.out.println("Usuario agregado: " + user);

			clearFields();

		} catch (Exception e) {
			errorLabel.setText("Error al agregar el usuario.");
		}
	}

	private void clearFields() {
		nameField.clear();
		emailField.clear();
		claveField.clear();
		roleField.clear();
		avatarField.clear();
	}

	private void clearErrors() {
		errorLabel.setText("");
	}
}
