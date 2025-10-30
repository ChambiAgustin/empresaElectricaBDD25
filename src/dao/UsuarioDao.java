package dao;

import db.DbManager;
import model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) para la entidad Usuario.
 *
 * ¿Por qué esta clase?
 * Es una "Capa de Acceso a Datos". Su única responsabilidad
 * es hablar con la tabla 'usuario' en la BD.
 * Separa la lógica de negocio (en App.java) de la lógica
 * de persistencia (SQL).
 *
 * Referencia Teórico 9 - Pág. 279 "Java DataBase Connectivity (JDBC)"
 */
public class UsuarioDao {

    /**
     * Inserta un nuevo usuario en la base de datos.
     * (Cumple el Punto 5a de la consigna)
     *
     * @param direccion La dirección del nuevo usuario.
     * @return El ID (id_usuario) autogenerado por la BD.
     * @throws SQLException Si ocurre un error de SQL.
     * @throws Exception Si ocurre un error al obtener la conexión.
     */
    public int insertarUsuario(String direccion) throws Exception {
        // SQL de Inserción.
        // Referencia Teórico 4 - Pág. 160 "Comando INSERT"
        // Usamos "?" para evitar Inyección SQL.
        String sql = "INSERT INTO usuario (direccion) VALUES (?)";
        int nuevoId = -1; // -1 indica que no se pudo insertar

        // 'try-with-resources' (introducido en Java 7)
        // Cierra automáticamente Connection y PreparedStatement al final,
        // incluso si hay un error. Evita fugas de memoria.
        try (Connection conn = DbManager.getConnection();
             // Referencia Teórico 9 - Pág. 290 "Interface PreparedStatement"
             // Es MÁS SEGURO y eficiente que 'Statement' porque pre-compila
             // el SQL y maneja los tipos de datos de forma segura.
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignamos el valor al primer "?" (índice 1)
            ps.setString(1, direccion);

            // Referencia Teórico 9 - Pág. 295 "int executeUpdate(String)"
            // Se usa para INSERT, UPDATE, DELETE. Retorna cuántas filas
            // fueron afectadas.
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                // Si la inserción fue exitosa, pedimos las llaves generadas
                // (en este caso, el 'id_usuario' AUTO_INCREMENT)
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        nuevoId = rs.getInt(1);
                    }
                }
            }
        }
        // 'conn' y 'ps' se cierran solos aquí gracias a try-with-resources
        return nuevoId;
    }

    /**
     * Método de ayuda para listar todos los usuarios (para pruebas).
     *
     * @return Una lista de objetos Usuario.
     * @throws Exception Si ocurre un error.
     */
    public List<Usuario> listarUsuarios() throws Exception {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        // Referencia Teórico 9 - Pág. 289 "Interface Statement"
        // Como este SQL es simple y no tiene parámetros (?),
        // podemos usar un Statement simple.
        try (Connection conn = DbManager.getConnection();
             Statement st = conn.createStatement();
             // Referencia Teórico 9 - Pág. 296 "Interface ResultSet"
             // 'rs' es como un puntero a una tabla de resultados
             // que está en la BD.
             ResultSet rs = st.executeQuery(sql)) {

            // Referencia Teórico 9 - Pág. 299 "Ejemplo" (while(resultSet.next()))
            // 'rs.next()' mueve el puntero a la siguiente fila.
            // Devuelve 'true' si hay una fila, 'false' si se acabó.
            while (rs.next()) {
                // Referencia Teórico 9 - Pág. 297 "Métodos de ResultSet"
                // Obtenemos los datos por el nombre de la columna.
                int id = rs.getInt("id_usuario");
                String dir = rs.getString("direccion");

                // Creamos un objeto 'Usuario' (del Modelo) y lo
                // agregamos a la lista.
                usuarios.add(new Usuario(id, dir));
            }
        }
        return usuarios;
    }
}
