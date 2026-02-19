package org.example.controlador.service;

import org.example.controlador.DTO.EmpleadoRequestDTO;
import org.example.controlador.DTO.EmpleadoResponseDTO;

public interface EmpleadoService {
    EmpleadoResponseDTO agregarEmpleado(EmpleadoRequestDTO requestDTO);
}
