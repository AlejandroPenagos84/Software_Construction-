package org.example.controlador.command;

import org.example.controlador.DTO.GranjeroDTO;
import org.example.controlador.services.GranjeroService;

import java.util.List;

public class CalcularFumigacionCommand implements Command<CommandResult<List<GranjeroDTO>>> {
    private final GranjeroService granjeroService;

    public CalcularFumigacionCommand(GranjeroService granjeroService) {
        this.granjeroService = granjeroService;
    }

    @Override
    public String name() {
        return "Calcular Fumigación";
    }

    @Override
    public CommandResult<List<GranjeroDTO>> execute() {
        List<GranjeroDTO> resultado = granjeroService.calcularFumigacion();
        return new CommandResult<>("Reporte de Fumigación", resultado, false);
    }
}
