package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Modelo que representa la tabla `reclamo`.
 * Columnas mapeadas (según DDL):
 *  - num_reclamo (INT AUTO_INCREMENT)
 *  - id_usuario (INT)         -> idUsuario
 *  - cod_motivo (INT)        -> codMotivo
 *  - fecha (DATE)            -> fecha
 *  - hora (TIME)             -> hora
 *  - fecha_res (DATE)        -> fechaRes (nullable)
 *
 * Usamos java.time.LocalDate / LocalTime para manipular fechas/horas.
 */
public class Reclamo {
    private int numReclamo;
    private int idUsuario;
    private int codMotivo;
    private LocalDate fecha;
    private LocalTime hora;
    private LocalDate fechaRes; // puede ser null si no resuelto
    private String descripcion;  // opcional, si tu DDL tiene descripción

    public Reclamo() {}

    public Reclamo(int numReclamo, int idUsuario, int codMotivo, LocalDate fecha, LocalTime hora, LocalDate fechaRes) {
        this.numReclamo = numReclamo;
        this.idUsuario = idUsuario;
        this.codMotivo = codMotivo;
        this.fecha = fecha;
        this.hora = hora;
        this.fechaRes = fechaRes;
    }

    public Reclamo(int idUsuario, int codMotivo, LocalDate fecha, LocalTime hora) {
        this.idUsuario = idUsuario;
        this.codMotivo = codMotivo;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getNumReclamo() {
        return numReclamo;
    }

    public void setNumReclamo(int numReclamo) {
        this.numReclamo = numReclamo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getCodMotivo() {
        return codMotivo;
    }

    public void setCodMotivo(int codMotivo) {
        this.codMotivo = codMotivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public LocalDate getFechaRes() {
        return fechaRes;
    }

    public void setFechaRes(LocalDate fechaRes) {
        this.fechaRes = fechaRes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Reclamo{" +
                "numReclamo=" + numReclamo +
                ", idUsuario=" + idUsuario +
                ", codMotivo=" + codMotivo +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", fechaRes=" + fechaRes +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}