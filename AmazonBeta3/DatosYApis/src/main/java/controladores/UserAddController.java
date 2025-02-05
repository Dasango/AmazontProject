package controladores;

import data.User;
import javafx.fxml.FXML;
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
    private TextField roleField;

    @FXML
    private TextField avatarField;

    @FXML
    private Label errorLabel;

    @FXML
    public void initialize() {
        // Inicialización de cualquier cosa si es necesario
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

            // Aquí se crearían los objetos de usuario y se los agregarían a una base de datos
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
