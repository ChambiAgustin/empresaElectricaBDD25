package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Gestiona la conexión con la base de datos.
 *
 * ¿Por qué esta clase?
 * Centraliza la lógica de conexión en un solo lugar. Si mañana
 * cambia la contraseña o el host, solo modificas esta clase (o mejor,
 * el archivo config.properties).
 *
 * Patrón Singleton: Nos aseguramos de que solo exista UNA instancia de
 * conexión (conn) en toda la aplicación, para reutilizarla y no
 * saturar a la base de datos abriendo y cerrando conexiones
 * innecesariamente.
 */
public class DbManager {
    // La única instancia de la conexión.
    private static Connection conn;

    // Hacemos el constructor privado para que nadie pueda crear
    // instancias de DbManager con 'new DbManager()'.
    private DbManager() {
        // Constructor privado previene instanciación.
    }

    /**
     * Método estático público para obtener la conexión.
     * Es la única puerta de entrada a la conexión.
     *
     * Referencia Teórico 9 - Pág. 284 "Clase DriverManager"
     *
     * @return La conexión a la base de datos.
     * @throws SQLException Si hay un error de SQL.
     * @throws IOException Si no encuentra el config.properties.
     */
    public static Connection getConnection() throws SQLException, IOException {
        // Si la conexión no existe (es la primera vez) o está cerrada,
        // la creamos.
        if (conn == null || conn.isClosed()) {
            // 1. Cargar las propiedades desde el archivo
            Properties props = new Properties();
            // FileInputStream abre el archivo de configuración.
            // 'config.properties' debe estar en la raíz del proyecto.
            props.load(new FileInputStream("config.properties"));

            // 2. Construir la URL de JDBC
            // Referencia Teórico 9 - Pág. 283 "Ejemplos de URLs JDBC" (para MySQL)
            String url = String.format(
                "jdbc:mysql://%s:%s/%s?serverTimezone=UTC",
                props.getProperty("host"),
                props.getProperty("port"),
                props.getProperty("database")
            );

            // 3. Obtener la conexión usando DriverManager
            // Referencia Teórico 9 - Pág. 284 "DriverManager.getConnection(...)"
            // Aquí es donde Java usa el "driver" (el .jar) para
            // "hablar" con MySQL.
            conn = DriverManager.getConnection(
                url,
                props.getProperty("user"),
                props.getProperty("password")
            );
        }
        // Devuelve la conexión existente o la recién creada.
        return conn;
    }

    /**
     * Cierra la conexión al final del programa.
     */
    public static void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
            System.out.println("Conexión a la BD cerrada.");
        }
    }
}
