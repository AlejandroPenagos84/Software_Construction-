package org.example.controlador.mapper;

import org.example.controlador.DTO.GranjeroDTO;
import org.example.modelo.Granjero;

public class GranjeroMapper {

    public static GranjeroDTO toDTO(Granjero granjero, double valorAPagar) {
        if (granjero == null) {
            return null;
        }
        return new GranjeroDTO(granjero.getNombre(), valorAPagar);
    }
}
