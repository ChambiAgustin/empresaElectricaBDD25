import dao.UsuarioDao;
import dao.ReclamoDao;
import db.DbManager;
import model.Usuario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que ejecuta las pruebas del Punto 5.
 *
 * ¿Por qué esta clase?
 * Es el "punto de entrada" (main) de tu programa.
 * Su responsabilidad es orquestar las operaciones:
 * 1. Pide datos al usuario (si es necesario).
 * 2. Llama a los métodos de los DAO (UsuarioDao, ReclamoDao).
 * 3. Muestra los resultados en la consola.
 *
 * NO contiene lógica SQL. Esa lógica está en los DAO.
 * Esto se llama "Separación de Responsabilidades".
 */
public class App {
    public static void main(String[] args) {
        // Scanner para leer la entrada del usuario desde la consola
        Scanner scanner = new Scanner(System.in);
        UsuarioDao udao = new UsuarioDao();
        ReclamoDao rdao = new ReclamoDao();

        try {
            System.out.println("--- INICIO DE PRUEBAS DEL PROYECTO ---");
            System.out.println("Conectando a la base de datos...");
            // Forzamos una conexión al inicio para verificar que
            // 'config.properties' y el driver .jar estén correctos.
            DbManager.getConnection();
            System.out.println("¡Conexión exitosa!");

            // --- PRUEBA PUNTO 5a: Insertar un usuario ---
            System.out.println("\n--- PRUEBA (Punto 5a): Insertar Usuario ---");
            System.out.print("Introduce la dirección para el nuevo usuario: ");
            String direccion = scanner.nextLine();

            int nuevoId = udao.insertarUsuario(direccion);
            System.out.println("Usuario insertado con ÉXITO. Nuevo ID: " + nuevoId);

            System.out.println("\n(Listando usuarios para verificar...)");
            List<Usuario> usuarios = udao.listarUsuarios();
            for (Usuario u : usuarios) {
                System.out.println("  ID: " + u.getIdUsuario() + ", Dir: " + u.getDireccion());
            }

            // --- PRUEBA PUNTO 5c: Listar reclamos con rellamados ---
            System.out.println("\n--- PRUEBA (Punto 5c): Listar Reclamos con Rellamados ---");
            System.out.print("Introduce el ID de un usuario para ver sus reclamos (Ej: 4, 5 o 1): ");
            int idUsuarioConsulta = Integer.parseInt(scanner.nextLine());

            System.out.println("Buscando reclamos para el usuario ID: " + idUsuarioConsulta + "...");
            rdao.listarReclamosConRellamados(idUsuarioConsulta);


            // --- PRUEBA PUNTO 5b: Eliminar un reclamo ---
            System.out.println("\n--- PRUEBA (Punto 5b): Eliminar Reclamo ---");
            // Para probar la eliminación, primero creamos un reclamo
            // que podamos borrar sin afectar los datos de prueba.
            System.out.println("(Creando reclamo temporal para eliminar...)");
            // Usamos el usuario 1 ('Energia del Sur S.A.') y motivo 1 ('Corte de energia')
            int idReclamoTemporal = rdao.insertarReclamo(1, 1, LocalDate.now(), LocalTime.now());
            System.out.println("Reclamo temporal creado con ID: " + idReclamoTemporal);

            System.out.println("Presiona ENTER para eliminar el reclamo ID: " + idReclamoTemporal);
            scanner.nextLine();

            boolean eliminado = rdao.eliminarReclamo(idReclamoTemporal);

            if (eliminado) {
                System.out.println("Reclamo ID: " + idReclamoTemporal + " eliminado con ÉXITO.");
                System.out.println("¡Verifica la tabla 'reclamos_eliminados' en MySQL para ver la auditoría del TRIGGER!");
            } else {
                System.out.println("ERROR: No se pudo eliminar el reclamo ID: " + idReclamoTemporal);
            }


            System.out.println("\n--- FIN DE LAS PRUEBAS ---");

        } catch (Exception e) {
            // Manejo de errores.
            System.out.println("\n--- !!! ERROR CRÍTICO !!! ---");
            System.out.println("Ha ocurrido un error. Mensaje: " + e.getMessage());
            System.out.println("\nPosibles causas:");
            System.out.println("1. ¿Está el archivo 'config.properties' en la raíz del proyecto?");
            System.out.println("2. ¿Son correctos el 'user' y 'password' en 'config.properties'?");
            System.out.println("3. ¿Está el archivo .jar de MySQL (driver) en la raíz del proyecto?");
            System.out.println("4. ¿Ejecutaste los scripts 'proyecto-ddl.sql' y 'proyecto-dml.sql' en MySQL?");
            System.out.println("\nDetalle del error (Stack Trace):");
            e.printStackTrace();
        } finally {
            // Cerramos la conexión y el scanner al final.
            scanner.close();
            try {
                DbManager.closeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

