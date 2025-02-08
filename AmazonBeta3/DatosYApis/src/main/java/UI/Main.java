package UI;

import java.io.IOException;

import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import accesoDatos.UserAd;
import data.Category;
import data.Product;
import javafx.application.Application;
import serv.CategoryService;
import serv.ProductService;
import serv.UserService;
import sincronizacion.CargarBase;

public class Main {

	public static void main(String[] args) throws IOException {
		
		
		//Conectar Api e importarlos a la base de datos
		
		CargarBase.sincroniarTodos();
				
		//Inicia GUI
		Inicio.main(args);
		
		
	}
}