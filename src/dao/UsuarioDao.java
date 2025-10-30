package dao;

import db.DbManager;
import java.sql.*;

public class UsuarioDao {

    // Inserta un nuevo usuario
    public int insertarUsuario(String direccion) throws Exception {
        String sql = "INSERT INTO usuario (direccion) VALUES (?)";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, direccion);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1); // devuelve id generado
            }
        }
        return -1;
    }

    // Muestra todos los usuarios
    public void listarUsuarios() throws Exception {
        String sql = "SELECT * FROM usuario";
        try (Connection c = DbManager.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id_usuario") + " - " + rs.getString("direccion"));
            }
        }
    }
}
