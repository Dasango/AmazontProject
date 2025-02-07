package sincronizacion;

import accesoDatos.CategoryAd;
import accesoDatos.ProductAd;
import accesoDatos.UserAd;
import data.*;
import serv.*;
 
import java.io.IOException;
import java.lang.classfile.instruction.NewMultiArrayInstruction;
import java.util.Iterator;
import java.util.List;

public class CargarBase {
	
		 
	private void sincronizarCategorias() {
			CategoryService categoryServ = new CategoryService();
			CategoryAd categoryAd = new CategoryAd();
	 
			List<Category> categorias = null;
	 
			try {
				categorias = categoryServ.getAllCategories();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : categorias) {
	 
				if (categoryAd.crear(a)) {
					System.out.println("Categoría insertada :3 : " + a);
				} else {
					System.out.println("Error al insertar la categoría con ID " + a.id() + " D:");
				}
			}
		}
		private void sincronizarProductos() {
			ProductService productServ = new ProductService();
			ProductAd productAd = new ProductAd();
	 
			List<Product> productos = null;
	 
			try {
				productos = productServ.getAllProducts();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : productos) {
	 
				if (productAd.crear(a)) {
					System.out.println("Producto insertado :3 : " + a);
				} else {
					System.out.println("Error al insertar el producto con ID " + a.id() + " D:");
				}
			}
		}
		private void sincronizarUsuarios() {
			UserService userServ = new UserService();
			UserAd userAd = new UserAd();
	 
			List<User> usuarios = null;
	 
			try {
				usuarios = userServ.getAllUsers();
			} catch (IOException e) {
				e.printStackTrace();
			}
	 
			for (var a : usuarios) {
	 
				if (userAd.crear(a)) {
					System.out.println("Usuarie insertade :3 : " + a);
				} else {
					System.out.println("Error al insertar el usuarie con ID " + a.id() + " D:");
				}
			}
		}
		public static void sincroniarTodos() {
			CargarBase m = new CargarBase();
			m.sincronizarCategorias();
			//m.sincronizarProductos();
			//m.sincronizarUsuarios();
		}


}
