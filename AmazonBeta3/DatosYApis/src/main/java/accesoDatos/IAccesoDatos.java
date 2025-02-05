package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public interface IAccesoDatos<T> {
	
	
	
	public static final String url = "jdbc:mysql://localhost:3306/tienda";
	public static final String usuario = "root";
	public static final String password = "root";
	
	List<T> obtenerTodos () ; 
	T obtenerPorId(int id); 
	boolean crear (T nuevo); 
	boolean actualizar (T nuevo);
	boolean eliminar(int id); 

	
	default Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, usuario, password);
	}

}
 