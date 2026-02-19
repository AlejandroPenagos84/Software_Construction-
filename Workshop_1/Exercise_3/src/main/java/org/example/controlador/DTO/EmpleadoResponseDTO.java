package org.example.controlador.DTO;

public class EmpleadoResponseDTO {
    private String codigo;
    private String nombre;
    private String sexo;
    private Double salarioBruto;
    private Double salarioNeto;
    private Double retencion;

    public EmpleadoResponseDTO(String codigo, String nombre, String sexo, Double salarioBruto,
                       Double salarioNeto, Double retencion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.salarioBruto = salarioBruto;
        this.salarioNeto = salarioNeto;
        this.retencion = retencion;
    }

    // Getters y Setters
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(Double salarioBruto) { this.salarioBruto = salarioBruto; }

    public Double getSalarioNeto() { return salarioNeto; }
    public void setSalarioNeto(Double salarioNeto) { this.salarioNeto = salarioNeto; }

    public Double getRetencion() { return retencion; }
    public void setRetencion(Double retencion) { this.retencion = retencion; }
}