package model;

/**
 * Modelo que representa la tabla `usuario`.
 * Mapea columnas:
 *  - id_usuario (INT AUTO_INCREMENT)
 *  - direccion (VARCHAR)
 *
 * Usamos este modelo en la capa DAO para recibir/retornar datos.
 */
public class Usuario {
    private int idUsuario;
    private String direccion;

    public Usuario() {}

    public Usuario(int idUsuario, String direccion) {
        this.idUsuario = idUsuario;
        this.direccion = direccion;
    }

    public Usuario(String direccion) {
        this.direccion = direccion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}
