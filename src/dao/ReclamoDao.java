package dao;

import db.DbManager;
import java.sql.*;

public class ReclamoDao {

    // Inserta un reclamo
    public int insertarReclamo(int idUsuario, int codMotivo, String fecha, String hora) throws Exception {
        String sql = "INSERT INTO reclamo (id_usuario, cod_motivo, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            ps.setInt(2, codMotivo);
            ps.setString(3, fecha);
            ps.setString(4, hora);
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
        }
        return -1;
    }

    // Elimina un reclamo (auditoría por trigger)
    public boolean eliminarReclamo(int idReclamo) throws Exception {
        String sql = "DELETE FROM reclamo WHERE num_reclamo = ?";
        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idReclamo);
            int rows = ps.executeUpdate();
            return rows > 0;
        }
    }

    // Listar reclamos con cantidad de rellamados
    public void listarReclamosConRellamados(int idUsuario) throws Exception {
        String sql = """
            SELECT r.num_reclamo, r.fecha, COUNT(l.num_llamado) AS rellamados
            FROM reclamo r
            LEFT JOIN llamado l ON r.num_reclamo = l.num_reclamo
            WHERE r.id_usuario = ?
            GROUP BY r.num_reclamo, r.fecha;
        """;

        try (Connection c = DbManager.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("Reclamo " + rs.getInt("num_reclamo") + " - Rellamados: " + rs.getInt("rellamados"));
            }
        }
    }
}
