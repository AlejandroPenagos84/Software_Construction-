package org.example.controlador.DTO;

public class GranjeroDTO {
    private String nombre;
    private Double valorAPagar;

    public GranjeroDTO(String nombre, Double valorAPagar) {
        this.nombre = nombre;
        this.valorAPagar = valorAPagar;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getValorAPagar() {
        return valorAPagar;
    }

    public void setValorAPagar(Double valorAPagar) {
        this.valorAPagar = valorAPagar;
    }
}
