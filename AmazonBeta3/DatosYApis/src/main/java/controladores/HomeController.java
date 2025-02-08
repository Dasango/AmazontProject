package controladores;

import accesoDatos.ProductAd;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class HomeController {
	
    
    @FXML
    private TextField searchBar;  
    @FXML
    private ImageView imageView;
    @FXML
    private Label title;
    @FXML
    private Label price;
    @FXML
    private Label category;
    @FXML
    private Label description;
    @FXML
    private Label error;

    
    @FXML
    public void initialize() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
            	handleSearch();
            }
        });
    }
    @FXML
    private void handleSearch() {
    	emptyAll();
        try {
        	var search= searchBar.getText().trim();
            searchBar.setText("");

            int productId = Integer.parseInt(search);

            var product = new ProductAd().obtenerPorId(productId);
                     
            if (product.images().length > 0) {
                String imageUrl = product.images()[0];                
                imageView.setImage(new Image(imageUrl));            
            } else {
            	error.setText("Sin imágenes.");
                title.setText(product.title());
                price.setText("Precio: "+product.price());
                category.setText("Categoría: "+product.category().name());
                description.setText("Descripción: "+ product.description());
            }
        } catch (NumberFormatException e) {
        	error.setText("Por favor, ingrese un número válido :(");
        } catch (Exception e) {
        	error.setText("No se encontro el producto :(");
        }
        
    }
  
    private void emptyAll() {
    	error.setText("");
        imageView.setImage(null); 
        title.setText("");
        price.setText("");
        category.setText("");
        description.setText("");
    }
}
