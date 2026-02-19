package org.example.modelo;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum TipoFumigacion {

    MALAS_HIERBAS(1, "Malas Hierbas", 10.0),
    LANGOSTAS(2, "Langostas", 15.0),
    GUSANOS(3, "Gusanos", 20.0),
    TODO(4, "Todo lo anterior", 30.0);

    private final int codigo;
    private final String descripcion;
    private final double precioPorHectarea;

    private static final Map<Integer, TipoFumigacion> MAP = Arrays.stream(values())
            .collect(Collectors.toMap(TipoFumigacion::getCodigo, e -> e));

    TipoFumigacion(int codigo, String descripcion, double precioPorHectarea) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.precioPorHectarea = precioPorHectarea;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getPrecioPorHectarea() {
        return precioPorHectarea;
    }

    public static TipoFumigacion fromCodigo(int codigo) {
        TipoFumigacion tipo = MAP.get(codigo);
        if (tipo == null) {
            throw new IllegalArgumentException("Código inválido para TipoFumigacion: " + codigo);
        }
        return tipo;
    }
}
