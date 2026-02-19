package org.example.controlador.services;

import org.example.controlador.DTO.EmpleadoDTO;

import java.util.List;

public interface EmpleadoService {
    List<EmpleadoDTO> obtenerEmpleados();
}
