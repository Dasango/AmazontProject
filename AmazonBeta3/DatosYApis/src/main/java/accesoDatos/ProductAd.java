package accesoDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;


import data.Product;

public class ProductAd implements IAccesoDatos<Product> {

	@Override
	public boolean crear(Product producto) {
	    String query = "INSERT INTO producto (title, price, description, categoria) VALUES (?, ?, ?, ?)";

	    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, producto.title());
	        ps.setDouble(2, producto.price());
	        ps.setString(3, producto.description());
	        ps.setInt(4, producto.category().id());

	        if (ps.executeUpdate() > 0) {
	            // Obtener el ID generado
	            ResultSet rs = ps.getGeneratedKeys();
	            int generatedId = -1;
	            if (rs.next()) {
	                generatedId = rs.getInt(1);
	            } else {
	                System.out.println("Error: No se pudo obtener el ID del producto insertado.");
	                return false;
	            }

	            // Insertar imágenes
	            String queryImg = "INSERT INTO imgproducto (id, path) VALUES (?, ?)";

	            for (String img : producto.images()) {
	                try (PreparedStatement psImg = con.prepareStatement(queryImg)) {
	                    psImg.setInt(1, generatedId);  // ✅ Usamos el ID generado
	                    psImg.setString(2, img);

	                    if (psImg.executeUpdate() <= 0) {
	                        System.out.println("Para el producto " + generatedId + " — " + producto.title());
	                        System.out.println("No se pudo insertar la imagen: " + img);
	                    }
	                } catch (SQLException e) {
	                    System.out.println("Para el producto " + generatedId + " — " + producto.title());
	                    System.out.println("No se pudo insertar la imagen: " + img);
	                    e.printStackTrace();
	                }
	            }
	            return true;
	        }
	    } catch (SQLException e) {
	        System.out.println("Error al crear el producto " + producto.title());
	        e.printStackTrace();
	    }

	    return false;
	}


	public boolean crearApi(Product producto) {
		String query = "INSERT INTO producto (id, title, price, description, categoria) VALUES (?, ?, ?, ?, ?)";

		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {

			ps.setInt(1, producto.id());
			ps.setString(2, producto.title());
			ps.setDouble(3, producto.price());
			ps.setString(4, producto.description());
			ps.setInt(5, producto.category().id());

			if (ps.executeUpdate() > 0) {

				// Insertar imágenes
				String queryImg = "INSERT INTO imgproducto ( id, path) VALUES (?, ?)";
				int cont = 1;

				for (String img : producto.images()) {
					try (PreparedStatement psImg = con.prepareStatement(queryImg)) {
						psImg.setInt(1, producto.id());
						psImg.setString(2, img);

						if (psImg.executeUpdate() <= 0) {
							System.out.println("Para el producto " + producto.id() + " — " + producto.title());
							System.out.println("No se pudo insertar la imagen: " + img);
						}
					} catch (SQLException e) {
						System.out.println("Para el producto " + producto.id() + " — " + producto.title());
						System.out.println("No se pudo insertar la imagen: " + img);
						e.printStackTrace();
					}
				}
				return true;
			}
		} catch (SQLException e) {
			System.out.println("Error al crear el producto " + producto.id() + " — " + producto.title());
			e.printStackTrace();
		}

		return false;
	}

	private String[] obtenerImg(int productId) {
	    List<String> images = new ArrayList<>();
	    String query = "SELECT path FROM imgproducto WHERE id = ?"; 
	    try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
	        ps.setInt(1, productId);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	            images.add(rs.getString("path"));
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return images.toArray(new String[0]);
	}


	@Override
	public List<Product> obtenerTodos() {
		List<Product> products = new ArrayList<>();
		String query = "SELECT * FROM producto";
		try (Connection con = getConnection();
				PreparedStatement ps = con.prepareStatement(query);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				products.add(new Product(rs.getInt("id"), rs.getString("title"), rs.getDouble("price"),
						rs.getString("description"), null, obtenerImg(rs.getInt("id"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	@Override
	public Product obtenerPorId(int id) {
		String query = "SELECT * FROM producto WHERE id = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Product(rs.getInt("id"), rs.getString("title"), rs.getDouble("price"),
						rs.getString("description"), new CategoryAd().obtenerPorId(rs.getInt("categoria")), obtenerImg(id));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean actualizar(Product nuevo) {
		String query = "UPDATE producto SET title = ?, price = ?, description = ?, categoria = ? WHERE id = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setString(1, nuevo.title());
			ps.setDouble(2, nuevo.price());
			ps.setString(3, nuevo.description());
			ps.setInt(4, nuevo.category() != null ? nuevo.category().id() : null);
			ps.setInt(5, nuevo.id());
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean eliminar(int id) {
		String query = "DELETE FROM producto WHERE id = ?";
		try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, id);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}