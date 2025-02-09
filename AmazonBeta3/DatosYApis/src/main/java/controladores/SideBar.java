package controladores;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class SideBar {

    @FXML
    private BorderPane contentPane;
    
    public void showHome() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/HomeView.fxml")));
		System.out.println("Carga Pagina Principal");
	}
    
    public void showAll() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/ShowAll.fxml")));
		System.out.println("Carga Pagina Principal");
	}
    
    public void showProduct() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/ProductAdd.fxml")));
		System.out.println("Carga Producto");
	}
    
    public void showCategory() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/CategoryAdd.fxml")));
		System.out.println("Carga Categoria");
	}
    public void showUser() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/UserAdd.fxml")));
		System.out.println("Carga User");
	}
    
    public void showEProduct() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/ProductEdit.fxml")));
		System.out.println("Carga ProductoEdit");
	}
    
    public void showECategory() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/CategoryEdit.fxml")));
		System.out.println("Carga CategoriaEdit");
	}
    public void showEUser() throws IOException {
    	contentPane.setCenter(FXMLLoader.load(getClass().getResource("/resources/UserEdit.fxml")));		System.out.println("Carga UserEdit");
	}
}
