package org.example.controlador.DTO;

import org.example.modelo.ColorCabello;
import org.example.modelo.ColorOjos;

public class ClienteDTO {
    private String nombre;
    private Integer edad;
    private Double altura;
    private Double peso;
    private String colorOjos;
    private String colorCabello;


    public ClienteDTO(String nombre, Integer edad, Double altura, Double peso, String colorOjos, String colorCabello) {
        this.nombre = nombre;
        this.edad = edad;
        this.altura = altura;
        this.peso = peso;
        this.colorOjos = colorOjos;
        this.colorCabello = colorCabello;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getColorCabello() {
        return colorCabello;
    }

    public void setColorCabello(String colorCabello) {
        this.colorCabello = colorCabello;
    }

    public String getColorOjos() {
        return colorOjos;
    }

    public void setColorOjos(String colorOjos) {
        this.colorOjos = colorOjos;
    }
}
