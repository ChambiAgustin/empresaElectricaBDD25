import dao.UsuarioDao;
import dao.ReclamoDao;

public class App {
    public static void main(String[] args) {
        try {
            UsuarioDao udao = new UsuarioDao();
            ReclamoDao rdao = new ReclamoDao();

            System.out.println("Usuarios actuales:");
            udao.listarUsuarios();

            System.out.println("\nInsertando nuevo reclamo...");
            int nuevo = rdao.insertarReclamo(1, 1, "2025-10-30", "15:30:00");
            System.out.println("Nuevo reclamo: " + nuevo);

            System.out.println("\nEliminando reclamo (ver auditoría)...");
            rdao.eliminarReclamo(nuevo);
            System.out.println("Reclamo eliminado correctamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
