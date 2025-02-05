package controladores;

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
    private Button add;
    
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

            var product = serv.ProductService.getProductById(productId);

            if (product != null && product.images().length > 0) {
                String imageUrl = product.images()[0];                
                imageView.setImage(new Image(imageUrl));
                title.setText(product.title());
                price.setText("Precio: "+product.price());
                category.setText("Categoría: "+product.category().name());
                description.setText("Descripción: "+ product.description());
                add.setOpacity(1);

            } else {
                System.out.println("Producto no encontrado o sin imágenes.");
                
            }
        } catch (NumberFormatException e) {
        	error.setText("Por favor, ingrese un número válido :(");
        } catch (Exception e) {
        	error.setText("No se encontro el producto :(");
        }
    }
    
    @FXML
    public void addToCart() {
        
    	//logica
    	
    	error.setText("Producto añadido: " + title.getText());
    }
    
    private void emptyAll() {
    	error.setText("");
        imageView.setImage(null); 
        title.setText("");
        price.setText("");
        category.setText("");
        description.setText("");
        add.setOpacity(0);

    }
}
