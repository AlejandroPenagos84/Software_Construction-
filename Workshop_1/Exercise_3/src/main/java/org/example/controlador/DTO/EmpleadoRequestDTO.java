package org.example.controlador.DTO;

public class EmpleadoRequestDTO {
    private String codigo;
    private String nombre;
    private String sexo;
    private Integer numHoras;
    private Double tarifaHora;

    public EmpleadoRequestDTO(String codigo, String nombre, String sexo, Integer numHoras, Double tarifaHora) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.numHoras = numHoras;
        this.tarifaHora = tarifaHora;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Integer getNumHoras() { return numHoras; }
    public void setNumHoras(Integer numHoras) { this.numHoras = numHoras; }

    public Double getTarifaHora() { return tarifaHora; }
    public void setTarifaHora(Double tarifaHora) { this.tarifaHora = tarifaHora; }
}