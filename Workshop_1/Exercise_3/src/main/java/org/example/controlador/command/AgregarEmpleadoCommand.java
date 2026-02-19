package org.example.controlador.command;

import org.example.controlador.DTO.EmpleadoRequestDTO;
import org.example.controlador.DTO.EmpleadoResponseDTO;
import org.example.controlador.service.EmpleadoService;

public class AgregarEmpleadoCommand implements ResponseCommand<EmpleadoResponseDTO> {
    private final EmpleadoService empleadoService;

    public AgregarEmpleadoCommand(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Override
    public String name() {
        return "Agregar Empleado";
    }

    @Override
    public CommandResult<EmpleadoResponseDTO> execute(EmpleadoRequestDTO requestDTO) {
        return new CommandResult<>("Empleado Agregado", empleadoService.agregarEmpleado(requestDTO), false);
    }
}
