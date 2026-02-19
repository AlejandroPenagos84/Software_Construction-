package org.example.modelo;

public class Granjero {
    private String nombre;
    private Double numHectareas;
    private TipoFumigacion tipoFumigacion;

    public Granjero(String nombre, Double numHectareas, TipoFumigacion tipoFumigacion) {
        this.nombre = nombre;
        this.numHectareas = numHectareas;
        this.tipoFumigacion = tipoFumigacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getNumHectareas() {
        return numHectareas;
    }

    public void setNumHectareas(Double numHectareas) {
        this.numHectareas = numHectareas;
    }

    public TipoFumigacion getTipoFumigacion() {
        return tipoFumigacion;
    }

    public void setTipoFumigacion(TipoFumigacion tipoFumigacion) {
        this.tipoFumigacion = tipoFumigacion;
    }
}
