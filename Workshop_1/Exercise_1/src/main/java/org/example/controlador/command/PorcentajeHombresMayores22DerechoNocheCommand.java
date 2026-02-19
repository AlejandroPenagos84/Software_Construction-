package org.example.controlador.command;

import org.example.controlador.service.EstudianteService;

public class PorcentajeHombresMayores22DerechoNocheCommand implements Command<CommandResult<Double>> {
    private final EstudianteService estudianteService;

    public PorcentajeHombresMayores22DerechoNocheCommand(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public String name() {
        return "Porcentaje Hombres Mayores 22 Derecho Noche";
    }

    @Override
    public CommandResult<Double> execute() {
        Double resultado = estudianteService.porcentajeHombresMayores22DerechoNoche();
        return new CommandResult<>("Porcentaje Hombres Mayores 22 Derecho Noche", resultado, false);
    }
}
