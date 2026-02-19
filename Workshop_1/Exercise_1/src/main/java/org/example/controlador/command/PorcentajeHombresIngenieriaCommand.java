package org.example.controlador.command;

import org.example.controlador.service.EstudianteService;

public class PorcentajeHombresIngenieriaCommand implements Command<CommandResult<Double>> {
    private final EstudianteService estudianteService;

    public PorcentajeHombresIngenieriaCommand(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public String name() {
        return "Porcentaje Hombres Ingeniería";
    }

    @Override
    public CommandResult<Double> execute() {
        Double resultado = estudianteService.porcentajeHombresIngenieria();
        return new CommandResult<>("Porcentaje Hombres Ingeniería", resultado, false);
    }
}
