package org.example.controlador.DTO;

public class CandidatoDTO {
    private String nombre;
    private Integer numeroVotaciones;
    private String estado; // Para mostrar mensaje de ganador/anulado etc si es necesario

    public CandidatoDTO(String nombre, Integer numeroVotaciones) {
        this.nombre = nombre;
        this.numeroVotaciones = numeroVotaciones;
        this.estado = "";
    }

    public CandidatoDTO(String nombre, Integer numeroVotaciones, String estado) {
        this.nombre = nombre;
        this.numeroVotaciones = numeroVotaciones;
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNumeroVotaciones() {
        return numeroVotaciones;
    }

    public void setNumeroVotaciones(Integer numeroVotaciones) {
        this.numeroVotaciones = numeroVotaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre + ": " + numeroVotaciones + " votos " + (estado.isEmpty() ? "" : "(" + estado + ")");
    }
}
