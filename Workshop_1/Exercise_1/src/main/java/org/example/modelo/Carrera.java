package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Carrera {

    INGENIERIA(1, "Ingeniería"),
    CONTADURIA(2, "Contaduria"),
    DERECHO(3, "Derecho"),
    OTRA(4, "Otra");

    private final int codigo;
    private final String descripcion;

    private static final Map<Integer, Carrera> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(Carrera::getCodigo, e -> e));

    Carrera(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Carrera fromCodigo(int codigo) {
        Carrera carrera = MAP.get(codigo);
        if (carrera == null) {
            throw new IllegalArgumentException("Código inválido para Carrera: " + codigo);
        }
        return carrera;
    }
}
