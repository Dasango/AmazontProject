package sincronizacion;

import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import accesoDatos.UserAd;
import data.*;
import serv.*;
 
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

public class CargarBase {
	


		 
	private void sincronizarCategorias() {
		
			CategoryAd categoryAd = new CategoryAd();
	 
			List<Category> categorias = null;
	 
			try {
				categorias = CategoryService.getAllCategories();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : categorias) {
	 
				if (categoryAd.crearApi(a)) {
					System.out.println("Categoría insertada: " + a);
				} else {
					System.out.println("Error al insertar la categoría con ID " + a.id() + " D:");
				}
			}
		}
		private void sincronizarProductos() {
			
			ProductAd productAd = new ProductAd();
	 
			List<Product> productos = null;
	 
			try {
				productos = ProductService.getAllProducts();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : productos) {
	 
				if (productAd.crearApi(a)) {
					System.out.println("Producto insertado: " + a);
				} else {
					System.out.println("Error al insertar el producto con ID " + a.id() + " D:");
				}
			}
		}
		private void sincronizarUsuarios() {
			
			UserAd userAd = new UserAd();
	 
			List<User> usuarios = null;
	 
			try {
				usuarios = UserService.getAllUsers();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : usuarios) {
	 
				if (userAd.crearApi(a)) {
					System.out.println("Usuario insertado: " + a);
				} else {
					System.out.println("Error al insertar el usuarie con ID " + a.id() + " D:");
				}
			}
		}
		public static void sincroniarTodos() {
			CargarBase m = new CargarBase();
			m.sincronizarCategorias();
			m.sincronizarProductos();
			m.sincronizarUsuarios();
		}


}
