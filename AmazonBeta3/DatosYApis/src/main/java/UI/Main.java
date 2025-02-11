package UI;

import java.io.IOException;

import data.User;
import serv.CategoryService;
import serv.ProductService;
import serv.UserService;
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
		

		//CargarBase.sincronizarTodos();
				
		//Inicia GUI
	
		Inicio.main(args);
		
		
		//Verificamos la api
		CategoryService.getAllCategories().forEach(System.out::println);
		UserService.getAllUsers().forEach(System.out::println);
		ProductService.getAllProducts().forEach(System.out::println);

		

	}
}