package controladores;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import data.Category;
import data.Product;
import data.User;
import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import accesoDatos.UserAd;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowAllController implements Initializable {

    @FXML
    private HBox productosContainer;
    @FXML
    private HBox usuariosContainer;
    @FXML
    private HBox categoriasContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargarProductos();
        cargarUsuarios();
        cargarCategorias();
    }
    
    private void cargarProductos() {
        List<Product> productos = new ProductAd().obtenerTodos();

        for (Product producto : productos) {
            VBox productBox = new VBox(5); 
            ImageView imageView;
                       
            try {
                imageView = new ImageView(new Image(producto.images()[0], true));
            } catch (Exception e) {
                imageView = new ImageView(new Image("/resources/No_Image_Available.jpg")); 
            }
            
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Label idLabel = new Label("ID: " + producto.id());

            productBox.getChildren().addAll(imageView, idLabel);
            productosContainer.getChildren().add(productBox);
        }
    }
    
    
    private void cargarUsuarios() {
        List<User> productos = new UserAd().obtenerTodos();

        for (User producto : productos) {
            VBox productBox = new VBox(5); 
            ImageView imageView;
            
            try {
                imageView = new ImageView(new Image(producto.avatar(), true));
            } catch (Exception e) {
                imageView = new ImageView(new Image("/resources/No_Image_Available.jpg")); 
            }
            
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Label idLabel = new Label("ID: " + producto.id());

            productBox.getChildren().addAll(imageView, idLabel);
            usuariosContainer.getChildren().add(productBox);
        }
    }
    
    private void cargarCategorias() {
        List<Category> productos = new CategoryAd().obtenerTodos();

        for (Category producto : productos) {
            VBox productBox = new VBox(5); 
            ImageView imageView;
            
            try {
                imageView = new ImageView(new Image(producto.image(), true));
            } catch (Exception e) {
                imageView = new ImageView(new Image("/resources/No_Image_Available.jpg")); 
            }
            
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Label idLabel = new Label("ID: " + producto.id());

            productBox.getChildren().addAll(imageView, idLabel);
            categoriasContainer.getChildren().add(productBox);
        }
    }
}