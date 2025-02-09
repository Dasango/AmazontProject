package controladores;

import java.util.Optional;

import accesoDatos.UserAd;
import data.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class UserEditController {
    @FXML
    private TextField searchBar;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
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

    private Integer currentUserId = null; // Variable para guardar el ID del usuario actual

    @FXML
    public void initialize() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                getUser();
            }
        });
        roleComboBox.setItems(FXCollections.observableArrayList("admin", "customer", "seller"));
    }

    @FXML
    public void getUser() {
        try {
            String search = searchBar.getText().trim();
            System.out.println("Valor del campo searchBar: " + search);

            if (search.isEmpty() || !search.matches("\\d+")) {
                errorLabel.setText("Por favor, ingrese un número válido :(");
                editButton.setDisable(true);
                deleteButton.setDisable(true); // Deshabilitar el botón de eliminar
                return;
            }

            int userId = Integer.parseInt(search);
            var user = new UserAd().obtenerPorId(userId);

            if (user != null) {
                currentUserId = userId; // Guardamos el ID en la variable de instancia
                errorLabel.setText("Editando usuario con ID: " + userId);

                nameField.setText(user.name());
                emailField.setText(user.email());
                claveField.setText(user.password());
                roleComboBox.setValue(user.role());
                avatarField.setText(user.avatar());

                editButton.setDisable(false);
                deleteButton.setDisable(false); // Habilitar el botón de eliminar cuando se encuentra el usuario
            } else {
                errorLabel.setText("Usuario no encontrado");
                editButton.setDisable(true);
                deleteButton.setDisable(true); // Deshabilitar el botón de eliminar si no se encuentra el usuario
            }
        } catch (Exception e) {
            errorLabel.setText("No se encontró el usuario :(");
            editButton.setDisable(true);
            deleteButton.setDisable(true); // Deshabilitar el botón de eliminar si ocurre un error
        }
    }

    @FXML
    private void handleDeleteUser() {
        try {
            if (currentUserId == null) {
                errorLabel.setText("Debe buscar un usuario antes de eliminar.");
                return;
            }

            // Verificar si el usuario existe con el currentUserId
            var user = new UserAd().obtenerPorId(currentUserId);
            if (user == null) {
                errorLabel.setText("Usuario no encontrado.");
                return;
            }

            // Confirmación de eliminación
            boolean confirm = showConfirmationDialog();
            if (!confirm) {
                return; // Si el usuario no confirma, no se elimina
            }

            // Intentamos eliminar el usuario
            boolean deleted = new UserAd().eliminar(currentUserId);

            if (deleted) {
                errorLabel.setText("Usuario eliminado correctamente.");
                clearFields();
            } else {
                errorLabel.setText("No se pudo eliminar el usuario.");
            }

        } catch (Exception e) {
            errorLabel.setText("Error al eliminar el usuario.");
            e.printStackTrace();
        }
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar este usuario?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    @FXML
    private void handleEditUser() {
        clearErrors();

        try {
            if (currentUserId == null) {
                errorLabel.setText("Debe buscar un usuario antes de editar.");
                return;
            }

            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String clave = claveField.getText().trim();
            String role = roleComboBox.getValue();
            String avatar = avatarField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || clave.isEmpty() || role == null || avatar.isEmpty()) {
                errorLabel.setText("Todos los campos son obligatorios.");
                return;
            }

            var user = new User(currentUserId, name, email, clave, role, avatar);
            UserAd userAd = new UserAd();
            boolean updated = userAd.actualizar(user);

            if (updated) {
                System.out.println("Usuario actualizado: " + user);
                errorLabel.setText("Usuario actualizado correctamente.");
                clearFields();
                currentUserId = null; // Resetear el ID después de editar
            } else {
                errorLabel.setText("No se pudo actualizar el usuario.");
            }

        } catch (Exception e) {
            errorLabel.setText("Error al editar el usuario: " + e.getMessage());
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

