package controladores;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class SideBar {

    @FXML
    private BorderPane contentPane;
    
    public void showHome() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getHomeView());
		System.out.println("Carga Pagina Principal");
	}
    
    public void showProduct() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getProductView());
		System.out.println("Carga Producto");
	}
    
    public void showCategory() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getCategoryView());
		System.out.println("Carga Categoria");
	}
    public void showUser() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getUserView());
		System.out.println("Carga User");
	}
    
    public void showEProduct() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getProductEView());
		System.out.println("Carga ProductoE");
	}
    
    public void showECategory() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getCategoryEView());
		System.out.println("Carga CategoriaE");
	}
    public void showEUser() throws IOException {
    	contentPane.setCenter(new Tabs.Views().getUserEView());
		System.out.println("Carga UserE");
	}
}
