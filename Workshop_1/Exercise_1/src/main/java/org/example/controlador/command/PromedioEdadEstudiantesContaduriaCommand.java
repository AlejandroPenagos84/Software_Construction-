package org.example.controlador.command;

import org.example.controlador.service.EstudianteService;

public class PromedioEdadEstudiantesContaduriaCommand implements Command<CommandResult<Double>> {
    private final EstudianteService estudianteService;

    public PromedioEdadEstudiantesContaduriaCommand(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @Override
    public String name() {
        return "Promedio Edad Estudiantes Contaduría";
    }

    @Override
    public CommandResult<Double> execute() {
        Double resultado = estudianteService.promedioEdadEstudiantesContaduria();
        return new CommandResult<>("Promedio Edad Estudiantes Contaduría", resultado, false);
    }
}
