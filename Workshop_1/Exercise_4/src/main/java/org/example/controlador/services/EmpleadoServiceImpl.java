package org.example.controlador.services;

import org.example.controlador.DTO.EmpleadoDTO;
import org.example.controlador.mapper.EmpleadoMapper;
import org.example.modelo.Empleado;
import org.example.repository.EmpleadoRepository;

import java.util.List;

public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleados() {
        return empleadoRepository.obtenerEmpleados().stream().map(EmpleadoMapper::toDTO).toList();
    }
}
