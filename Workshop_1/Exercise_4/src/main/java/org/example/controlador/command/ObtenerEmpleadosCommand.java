package org.example.controlador.command;

import org.example.controlador.DTO.EmpleadoDTO;
import org.example.controlador.services.EmpleadoService;

import java.util.List;

public class ObtenerEmpleadosCommand implements Command<CommandResult<List<EmpleadoDTO>>> {
    private final EmpleadoService empleadoService;

    public ObtenerEmpleadosCommand(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @Override
    public String name() {
        return "Obtener Empleados";
    }

    @Override
    public CommandResult<List<EmpleadoDTO>> execute() {
        List<EmpleadoDTO> empleados = empleadoService.obtenerEmpleados();
        return new CommandResult<>("Obtener Empleados", empleados, false);
    }
}
