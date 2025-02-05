package accesoDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.User;

public class UsuarioAd implements IAccesoDatos<User> {

    @Override
    public boolean crear(User nuevo) {
        String query = "INSERT INTO usuario (id, name, email, password, role, avatar) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, nuevo.id());
            ps.setString(2, nuevo.name());
            ps.setString(3, nuevo.email());
            ps.setString(4, nuevo.password());
            ps.setString(5, nuevo.role());
            ps.setString(6, nuevo.avatar());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> obtenerTodos() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM usuario";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                users.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("avatar")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User obtenerPorId(int id) {
        String query = "SELECT * FROM usuario WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("avatar")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean actualizar(User nuevo) {
        String query = "UPDATE usuario SET name = ?, email = ?, password = ?, role = ?, avatar = ? WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nuevo.name());
            ps.setString(2, nuevo.email());
            ps.setString(3, nuevo.password());
            ps.setString(4, nuevo.role());
            ps.setString(5, nuevo.avatar());
            ps.setInt(6, nuevo.id());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int id) {
        String query = "DELETE FROM usuario WHERE id = ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
