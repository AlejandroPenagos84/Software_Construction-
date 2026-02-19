package org.example.controlador.command;

import org.example.controlador.service.EstudianteService;

public class PorcentajeMujeresMenores20Command implements Command<CommandResult<Double>> {
    private final EstudianteService estudianteService;

    public PorcentajeMujeresMenores20Command(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public String name() {
        return "Porcentaje Mujeres Menores de 20";
    }

    @Override
    public CommandResult<Double> execute() {
        Double resultado = estudianteService.porcentajeMujeresMenores20();
        return new CommandResult<>("Porcentaje Mujeres Menores de 20", resultado, false);
    }
}
