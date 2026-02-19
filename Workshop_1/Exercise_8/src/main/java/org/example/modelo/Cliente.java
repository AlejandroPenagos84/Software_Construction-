package org.example.modelo;

import java.awt.*;

public class Cliente {
    private String nombre;
    private Character sexo;
    private Integer edad;
    private Double altura;
    private Double peso;
    private ColorOjos colorOjos;
    private ColorCabello colorCabello;

    public Cliente(String nombre, Character sexo, Integer edad, Double altura,
                   Double peso, ColorOjos colorOjos, ColorCabello colorCabello) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.edad = edad;
        this.altura = altura;
        this.peso = peso;
        this.colorOjos = colorOjos;
        this.colorCabello = colorCabello;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public ColorOjos getColorOjos() {
        return colorOjos;
    }

    public void setColorOjos(ColorOjos colorOjos) {
        this.colorOjos = colorOjos;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public ColorCabello getColorCabello() {
        return colorCabello;
    }

    public void setColorCabello(ColorCabello colorCabello) {
        this.colorCabello = colorCabello;
    }

    public void setColorOjosPorCodigo(int codigo) {
        this.colorOjos = ColorOjos.fromCodigo(codigo);
    }

    public void setCOlorCabelloPorCodigo(int codigo) {
        this.colorCabello = ColorCabello.fromCodigo(codigo);
    }

}
