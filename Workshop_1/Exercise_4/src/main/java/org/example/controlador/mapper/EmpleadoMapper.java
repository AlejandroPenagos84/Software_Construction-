package org.example.controlador.mapper;

import org.example.controlador.DTO.EmpleadoDTO;
import org.example.modelo.Empleado;

public class EmpleadoMapper {
    public static EmpleadoDTO toDTO(Empleado empleado) {
        empleado.calcularDevengado();
        empleado.calcularSalarioNeto();
        
        return new EmpleadoDTO(
            empleado.getCodigo(),
            empleado.getNombre(),
            empleado.getDevengado(),
            empleado.getRetencion(),
            empleado.getSubsidio(),
            empleado.getSalarioNeto()
        );
    }
}

