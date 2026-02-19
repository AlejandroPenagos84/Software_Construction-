package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ColorCabello {

    CASTAÑO(1, "Castaño"),
    RUBIO(2, "Rubio"),
    OTRO(3, "Otro");

    private final int codigo;
    private final String descripcion;

    private static final Map<Integer, ColorCabello> MAP =
            Arrays.stream(values())
                    .collect(Collectors.toMap(ColorCabello::getCodigo, e -> e));

    ColorCabello(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static ColorCabello fromCodigo(int codigo) {
        ColorCabello colorCabello = MAP.get(codigo);
        if (colorCabello == null) {
            throw new IllegalArgumentException("Código inválido para ColorCabello: " + codigo);
        }
        return colorCabello;
    }
}