package org.example.modelo;

public class Empleado {
    private String codigo;
    private String nombre;
    private Double salarioHora;
    private Integer numHoras;
    private Integer numHijos;
    private Double retencion;
    private Integer subsidio;
    private Double devengado;
    private Double salarioNeto;

    public Empleado(String codigo, String nombre, Double salarioHora, Integer numHijos, Integer numHoras) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioHora = salarioHora;
        this.numHijos = numHijos;
        this.numHoras = numHoras;
    }

    public void calcularSubsidio() {
        this.subsidio = numHijos * 17000;
    }

    public void calcularRetencion(int numHijos, double salario) {
        if (salario < 428000) {
            if (numHijos > 12)        this.retencion = (double) 0;
            else if (numHijos > 6)    this.retencion = (double) ((12 - numHijos) / 2);
            else                      this.retencion = 4.0;
        } else {
            if (numHijos < 5)         this.retencion = 5.0;
            else if (numHijos < 10)   this.retencion = (double) (10 / numHijos);
            else                      this.retencion = (double) 0;
        }
    }

    public void calcularDevengado() {
        this.devengado = this.salarioHora * this.numHoras;
    }

    public void calcularSalarioNeto() {
        this.salarioNeto = this.devengado - (this.devengado * (this.retencion / 100)) + this.subsidio;
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

    public Double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(Double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Integer getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(Integer numHoras) {
        this.numHoras = numHoras;
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

    public Double getDevengado() {
        return devengado;
    }

    public void setDevengado(Double devengado) {
        this.devengado = devengado;
    }

    public Double getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(Double salarioNeto) {
        this.salarioNeto = salarioNeto;
    }
}
