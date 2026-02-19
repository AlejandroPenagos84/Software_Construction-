package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ColorOjos {

    AZULES(1, "Azules"),
    CASTAÑO(2, "Castaño"),
    OTRO(3, "Otro");

    private final int codigo;
    private final String descripcion;

    private static final Map<Integer, ColorOjos> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ColorOjos::getCodigo, e -> e));

    ColorOjos(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static ColorOjos fromCodigo(int codigo) {
        ColorOjos colorOjos = MAP.get(codigo);
        if (colorOjos == null) {
            throw new IllegalArgumentException("Código inválido para ColorOjos: " + codigo);
        }
        return colorOjos;
    }
}