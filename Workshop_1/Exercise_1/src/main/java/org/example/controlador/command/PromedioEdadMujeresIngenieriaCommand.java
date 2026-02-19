package org.example.controlador.command;

import org.example.controlador.service.EstudianteService;

public class PromedioEdadMujeresIngenieriaCommand implements Command<CommandResult<Double>> {
    private final EstudianteService estudianteService;

    public PromedioEdadMujeresIngenieriaCommand(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public String name() {
        return "Promedio Edad Mujeres Ingeniería";
    }

    @Override
    public CommandResult<Double> execute() {
        Double resultado = estudianteService.promedioEdadMujeresIngenieria();
        return new CommandResult<>("Promedio Edad Mujeres Ingeniería", resultado, false);
    }
}
