package org.example.modelo;

public class Candidato {
    private String nombre;
    private Integer numeroVotaciones;

    public Candidato(String nombre, Integer numeroVotaciones) {
        this.nombre = nombre;
        this.numeroVotaciones = numeroVotaciones;
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
}
