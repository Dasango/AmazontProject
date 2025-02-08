package controladores;

import accesoDatos.UserAd;
import data.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UserAddController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField claveField;

    @FXML
    private ComboBox<String> roleComboBox; 

    @FXML
    private TextField avatarField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList("admin", "customer", "seller"));
    }

    @FXML
    private void handleAddUser() {
        clearErrors();

        try {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String clave = claveField.getText().trim();
            String role = roleComboBox.getValue(); 
            String avatar = avatarField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || clave.isEmpty() || role == null || avatar.isEmpty()) {
                errorLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            var user = new User(1, name, email, clave, role, avatar); 

            UserAd userAd = new UserAd();
            if (userAd.crear(user)) {
                errorLabel.setText("Usuario creado correctamente.");
                System.out.println("Usuario creado: " + user);
                clearFields(); 
            } else {
                errorLabel.setText("Error al crear el usuario.");
            }
        } catch (Exception e) {
            errorLabel.setText("Error al agregar el usuario.");
            e.printStackTrace(); 
        }
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        claveField.clear();
        roleComboBox.setValue(null); 
        avatarField.clear();
    }

    private void clearErrors() {
        errorLabel.setText("");
    }
}

