package model;

/**
 * Modelo que representa la tabla `usuario`.
 * Es un "POJO" (Plain Old Java Object).
 *
 * ¿Por qué esta clase?
 * Sirve para transportar datos de forma ordenada entre las capas
 * de la aplicación. En lugar de pasar un 'String' con la dirección,
 * pasamos un objeto 'Usuario' completo.
 *
 * Mapea columnas:
 * - id_usuario (INT AUTO_INCREMENT) -> idUsuario
 * - direccion (VARCHAR)             -> direccion
 */
public class Usuario {
    private int idUsuario;
    private String direccion;

    public Usuario() {}

    public Usuario(int idUsuario, String direccion) {
        this.idUsuario = idUsuario;
        this.direccion = direccion;
    }

    // Constructor sin ID, útil para crear nuevos usuarios
    // antes de insertarlos en la BD (ya que el ID es AUTO_INCREMENT).
    public Usuario(String direccion) {
        this.direccion = direccion;
    }

    // Getters y Setters
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