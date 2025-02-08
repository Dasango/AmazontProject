package controladores;

import accesoDatos.ProductAd;
import accesoDatos.UserAd;
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
// no funciona esta clase xdxdxd
	@FXML
	public void getUser() {
	    try {
	        String search = searchBar.getText().trim();
	        System.out.println("Valor del campo searchBar: " + search);  

	        // Verificación de entrada
	        if (search.isEmpty() || !search.matches("\\d+")) {
	            errorLabel.setText("Por favor, ingrese un número válido :(");
	            editButton.setDisable(true);
	            return;
	        }

	        searchBar.setText("");  // Limpiar el campo después de obtener el valor

	        int userId = Integer.parseInt(search);

	        var user = new UserAd().obtenerPorId(userId);

	        if (user != null) {
	            errorLabel.setText("Editando usuario con ID: " + userId);

	            nameField.setText(user.name());
	            emailField.setText(user.email());
	            claveField.setText(user.password());
	            roleField.setText(user.role());
	            avatarField.setText(user.avatar());

	            editButton.setDisable(false); 
	        } else {
	            errorLabel.setText("Usuario no encontrado");
	            editButton.setDisable(true);  
	        }
	    } catch (Exception e) {
	        errorLabel.setText("No se encontró el usuario :(");
	        editButton.setDisable(true);  
	    }
	}

	@FXML
	private void handleEditUser() {
	    clearErrors();

	    try {
	        String name = nameField.getText().trim();
	        String email = emailField.getText().trim();
	        String clave = claveField.getText().trim();
	        String role = roleField.getText().trim();
	        String avatar = avatarField.getText().trim();

	        if (name.isEmpty() || email.isEmpty() || clave.isEmpty() || role.isEmpty() || avatar.isEmpty()) {
	            errorLabel.setText("Todos los campos son obligatorios.");
	            return;
	        }

	        int userId = Integer.parseInt(searchBar.getText().trim());

	        var user = new User(userId, name, email, clave, role, avatar);

	        UserAd userAd = new UserAd();
	        boolean updated = userAd.actualizar(user);

	        if (updated) {
	            System.out.println("Usuario actualizado: " + user);
	            errorLabel.setText("Usuario actualizado correctamente.");
	            clearFields();
	        } else {
	            errorLabel.setText("No se pudo actualizar el usuario.");
	        }

	    } catch (NumberFormatException e) {
	        errorLabel.setText("ID del usuario inválido.");
	    } catch (Exception e) {
	        errorLabel.setText("Error al editar el usuario: " + e.getMessage());
	        e.printStackTrace();  // Imprime el detalle del error en la consola para depuración
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
