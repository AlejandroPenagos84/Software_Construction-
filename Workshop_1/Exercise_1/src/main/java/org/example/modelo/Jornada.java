package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Jornada {

    DIURNA(1, "Diurna"),
    NOCTURNA(2, "Nocturna");

    private final int codigo;
    private final String descripcion;

    private static final Map<Integer, Jornada> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(Jornada::getCodigo, e -> e));

    Jornada(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Jornada fromCodigo(int codigo) {
        Jornada jornada = MAP.get(codigo);
        if (jornada == null) {
            throw new IllegalArgumentException("Código inválido para Jornada: " + codigo);
        }
        return jornada;
    }
}
