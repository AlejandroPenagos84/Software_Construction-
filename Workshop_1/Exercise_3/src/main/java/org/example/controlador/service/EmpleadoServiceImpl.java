package org.example.controlador.service;

import org.example.controlador.DTO.EmpleadoRequestDTO;
import org.example.controlador.DTO.EmpleadoResponseDTO;
import org.example.controlador.mapper.EmpleadoMapper;
import org.example.modelo.Empleado;

public class EmpleadoServiceImpl implements EmpleadoService{
    @Override
    public EmpleadoResponseDTO agregarEmpleado(EmpleadoRequestDTO requestDTO) {
        Empleado empleado = EmpleadoMapper.toModel(requestDTO);
        return EmpleadoMapper.toDTO(empleado);
    }
}
