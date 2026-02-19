package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum Sexo {

    MASCULINO(1, "Masculino"),
    FEMENINO(2, "Femenino");

    private final int codigo;
    private final String descripcion;

    private static final Map<Integer, Sexo> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(Sexo::getCodigo, e -> e));

    Sexo(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static Sexo fromCodigo(int codigo) {
        Sexo sexo = MAP.get(codigo);
        if (sexo == null) {
            throw new IllegalArgumentException("Código inválido para Sexo: " + codigo);
        }
        return sexo;
    }
}
