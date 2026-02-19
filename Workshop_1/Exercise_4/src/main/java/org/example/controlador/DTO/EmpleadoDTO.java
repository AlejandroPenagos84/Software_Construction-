package org.example.controlador.DTO;

public class EmpleadoDTO {
    private String codigo;
    private String nombre;
    private Double devengado;
    private Double retencion;
    private Integer subsidio;
    private Double totalAPagar;

    public EmpleadoDTO(String codigo, String nombre, Double devengado, Double retencion, Integer subsidio, Double totalAPagar) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.devengado = devengado;
        this.retencion = retencion;
        this.subsidio = subsidio;
        this.totalAPagar = totalAPagar;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getDevengado() {
        return devengado;
    }

    public void setDevengado(Double devengado) {
        this.devengado = devengado;
    }

    public Double getRetencion() {
        return retencion;
    }

    public void setRetencion(Double retencion) {
        this.retencion = retencion;
    }

    public Integer getSubsidio() {
        return subsidio;
    }

    public void setSubsidio(Integer subsidio) {
        this.subsidio = subsidio;
    }

    public Double getTotalAPagar() {
        return totalAPagar;
    }

    public void setTotalAPagar(Double totalAPagar) {
        this.totalAPagar = totalAPagar;
    }
}

