package dao;

import db.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Data Access Object (DAO) para la entidad Reclamo.
 * Contiene la lógica SQL para operar en las tablas
 * 'reclamo' y 'llamado'.
 */
public class ReclamoDao {

    /**
     * Inserta un nuevo reclamo. (No pedido, pero útil para probar 'eliminar').
     *
     * @param idUsuario ID del usuario que reclama
     * @param codMotivo Código del motivo
     * @param fecha     Fecha del reclamo
     * @param hora      Hora del reclamo
     * @return El ID (num_reclamo) autogenerado.
     * @throws Exception Si falla la inserción.
     */
    public int insertarReclamo(int idUsuario, int codMotivo, LocalDate fecha, LocalTime hora) throws Exception {
        String sql = "INSERT INTO reclamo (id_usuario, cod_motivo, fecha, hora) VALUES (?, ?, ?, ?)";
        int nuevoId = -1;

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignamos los parámetros (los "?")
            ps.setInt(1, idUsuario);
            ps.setInt(2, codMotivo);
            // Convertimos de java.time.LocalDate a java.sql.Date
            ps.setDate(3, Date.valueOf(fecha));
            // Convertimos de java.time.LocalTime a java.sql.Time
            ps.setTime(4, Time.valueOf(hora));

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        nuevoId = rs.getInt(1);
                    }
                }
            }
        }
        return nuevoId;
    }

    /**
     * Elimina un reclamo de la base de datos.
     * (Cumple el Punto 5b de la consigna)
     *
     * NOTA: La auditoría NO se hace en Java. Se ejecuta
     * automáticamente en la BD gracias a tu trigger
     * 'trigger_auditoria_eliminacion_reclamo' que definiste
     * en tu archivo DDL. ¡Excelente trabajo ahí!
     *
     * @param numReclamo El ID (num_reclamo) a eliminar.
     * @return true si se eliminó (1 fila afectada), false si no.
     * @throws Exception Si ocurre un error.
     */
    public boolean eliminarReclamo(int numReclamo) throws Exception {
        // Referencia Teórico 4 - Pág. 165 "Comando DELETE"
        String sql = "DELETE FROM reclamo WHERE num_reclamo = ?";

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignamos el ID del reclamo al "?"
            ps.setInt(1, numReclamo);

            // executeUpdate() devuelve el N° de filas afectadas
            int filasAfectadas = ps.executeUpdate();

            // Si se afectó 1 fila, la eliminación fue exitosa.
            return filasAfectadas > 0;
        }
    }

    /**
     * Lista los reclamos de un usuario con la cantidad de rellamados.
     * (Cumple el Punto 5c de la consigna)
     *
     * @param idUsuario El ID del usuario a consultar.
     * @throws Exception Si ocurre un error.
     */
    public void listarReclamosConRellamados(int idUsuario) throws Exception {
        // Esta consulta es la clave.
        // Es una combinación de las consultas 'b' y 'c' del Punto 6.
        //
        // 1. SELECT r.num_reclamo...: Tomamos datos del reclamo.
        // 2. COUNT(l.num_llamado): Contamos cuántos llamados
        //    tiene asociados. Lo llamamos 'total_rellamados'.
        // 3. FROM reclamo r: Empezamos por la tabla de reclamos.
        // 4. LEFT JOIN llamado l:
        //    Referencia Teórico 4 - Pág. 154 "Reuniones (JOIN)"
        //    Usamos LEFT JOIN (en lugar de INNER JOIN) porque queremos
        //    que se listen los reclamos AUNQUE no tengan rellamados
        //    (en ese caso, COUNT dará 0).
        // 5. WHERE r.id_usuario = ?: Filtramos por el usuario que nos
        //    piden (¡usamos PreparedStatement!).
        // 6. GROUP BY r.num_reclamo:
        //    Referencia Teórico 4 - Pág. 146 "Cláusula GROUP BY"
        //    Es OBLIGATORIO usar GROUP BY porque usamos una
        //    "Función Agregada" (COUNT). Agrupamos todas las filas
        //    de 'llamado' por cada 'num_reclamo'.
        String sql = """
            SELECT r.num_reclamo, r.fecha, r.hora, m.descripcion, COUNT(l.num_llamado) AS total_rellamados
            FROM reclamo r
            LEFT JOIN llamado l ON r.num_reclamo = l.num_reclamo
            LEFT JOIN motivo m ON r.cod_motivo = m.cod_motivo
            WHERE r.id_usuario = ?
            GROUP BY r.num_reclamo, r.fecha, r.hora, m.descripcion
            """;
            // Nota: Agregamos m.descripcion para que sea más legible.
            // Nota 2: Todo lo que está en el SELECT (excepto el COUNT)
            // debe estar en el GROUP BY.

        try (Connection conn = DbManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Asignamos el ID de usuario al "?"
            ps.setInt(1, idUsuario);

            // executeQuery() se usa para SELECT. Devuelve un ResultSet.
            // Referencia Teórico 9 - Pág. 295 "ResultSet executeQuery(String)"
            try (ResultSet rs = ps.executeQuery()) {
                boolean encontrado = false;
                while (rs.next()) {
                    encontrado = true;
                    // Obtenemos los datos de la fila actual
                    int numReclamo = rs.getInt("num_reclamo");
                    Date fecha = rs.getDate("fecha");
                    Time hora = rs.getTime("hora");
                    String motivo = rs.getString("descripcion");
                    int rellamados = rs.getInt("total_rellamados");

                    // Imprimimos el resultado formateado
                    System.out.printf(
                        "  -> Reclamo N°: %d | Fecha: %s %s | Motivo: %s | Rellamados: %d\n",
                        numReclamo, fecha, hora, motivo, rellamados
                    );
                }
                if (!encontrado) {
                    System.out.println("  -> El usuario no tiene reclamos registrados.");
                }
            }
        }
    }
}
