package org.example.modelo;

public class Empleado {
    private String codigo;
    private String nombre;
    private String sexo;
    private Integer numHoras;
    private Integer numHorasExtra;
    private Double impuestos;
    private Double salarioBruto;
    private Double salarioNeto;
    private Double retencion;

    public Empleado(String codigo, String nombre, String sexo, Integer numHoras, Integer numHorasExtra, Double salarioBruto, Double impuestos, Double salarioNeto, Double retencion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.numHoras = numHoras;
        this.numHorasExtra = numHorasExtra;
        this.salarioBruto = salarioBruto;
        this.impuestos = impuestos;
        this.salarioNeto = salarioNeto;
        this.retencion = retencion;
    }

    public Empleado(String codigo, String nombre, String sexo, Integer numHoras) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.sexo = sexo;
        this.numHoras = numHoras;
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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Integer getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(Integer numHoras) {
        this.numHoras = numHoras;
    }

    public Integer getNumHorasExtra() {
        return numHorasExtra;
    }

    // Calcula horas extra según las reglas
    public void setNumHorasExtra() {
        if (this.numHoras >= 240 && this.numHoras < 300) {
            this.numHorasExtra = this.numHoras - 240;
        } else if (this.numHoras >= 300) {
            this.numHorasExtra = this.numHoras - 300;
        } else {
            this.numHorasExtra = 0;
        }
    }

    public Double getSalarioBruto() {
        return salarioBruto;
    }

    // Calcula salario bruto según tarifa y horas
    public void setSalarioBruto(Double tarifaHora) {
        double salario = 0;

        if (this.numHoras < 240) {
            salario = this.numHoras * tarifaHora;

        } else if (this.numHoras >= 240 && this.numHoras < 300) {
            salario = 240 * tarifaHora; // horas normales
            salario += this.numHorasExtra * (tarifaHora * 2.5); // extras

        } else { // >= 300
            salario = 300 * tarifaHora; // horas normales
            salario += this.numHorasExtra * (tarifaHora * 1.7); // extras
        }

        this.salarioBruto = salario;
    }

    public Double getImpuestos() {
        return impuestos;
    }

    // Calcula impuestos según salario bruto
    public void setImpuestos() {
        if (this.salarioBruto < 900_000) {
            this.impuestos = 0.0;
        } else if (this.salarioBruto < 1_200_000) {
            this.impuestos = 0.05;
        } else if (this.salarioBruto < 2_000_000) {
            this.impuestos = 0.10;
        } else {
            this.impuestos = 0.20;
        }
    }

    public Double getRetencion() {
        return retencion;
    }

    // Calcula retención aplicando el porcentaje de impuestos al bruto
    public void setRetencion() {
        this.retencion = this.salarioBruto * this.impuestos;
    }

    public Double getSalarioNeto() {
        return salarioNeto;
    }

    // Calcula salario neto descontando la retención
    public void setSalarioNeto() {
        this.salarioNeto = this.salarioBruto - this.retencion;
    }
}
