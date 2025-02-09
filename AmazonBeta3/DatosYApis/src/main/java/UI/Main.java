package UI;

import java.io.IOException;

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