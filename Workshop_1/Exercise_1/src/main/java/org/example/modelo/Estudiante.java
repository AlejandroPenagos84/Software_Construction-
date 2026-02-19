package org.example.modelo;

public class Estudiante {
    private Integer edad;
    private Carrera carrera;
    private Jornada jornada;
    private Sexo sexo;

    public Estudiante(Carrera carrera, Jornada jornada, Sexo sexo, Integer edad) {
        this.carrera = carrera;
        this.jornada = jornada;
        this.sexo = sexo;
        this.edad = edad;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Jornada getJornada() {
        return jornada;
    }

    public void setJornada(Jornada jornada) {
        this.jornada = jornada;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }
}
