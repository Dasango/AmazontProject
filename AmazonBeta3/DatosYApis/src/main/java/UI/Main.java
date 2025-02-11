package UI;

import java.io.IOException;
import java.util.List;

import data.Category;
import data.Product;
import serv.CategoryService;
import serv.ProductService;
import sincronizacion.CargarBase;

/*	TRABAJO GRUPAL 
 * 
 * 	Integrantes:
 * 		Arteaga Richard
 * 		Diaz Adrían
 * 		Sango David
 * 		Suarez Jonathan
 * 
 */

public class Main {
	
	
	

	public static void main(String[] args) throws IOException {
		
		
		//Conectar Api e importarlos a la base de datos
		

		CargarBase.sincronizarTodos();
				
		//Inicia GUI
		
		Inicio.main(args);
		
		

		
	}
}