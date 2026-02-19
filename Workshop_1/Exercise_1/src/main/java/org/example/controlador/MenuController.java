package org.example.controlador;

import org.example.controlador.command.Command;
import org.example.controlador.command.CommandResult;
import org.example.controlador.command.PorcentajeHombresMayores22DerechoNocheCommand;
import org.example.controlador.command.PorcentajeHombresIngenieriaCommand;
import org.example.controlador.command.PorcentajeMujeresMenores20Command;
import org.example.controlador.command.PromedioEdadEstudiantesContaduriaCommand;
import org.example.controlador.command.PromedioEdadMujeresIngenieriaCommand;
import org.example.controlador.command.SalirCommand;
import org.example.controlador.service.EstudianteService;
import org.example.vista.VistaMenu;

import java.util.LinkedHashMap;
import java.util.Map;

public class MenuController {
    private final EstudianteService estudianteService;
    private final VistaMenu vistaMenu;
    private Map<Integer, Command<?>> commands;

    public MenuController(EstudianteService estudianteService, VistaMenu vistaMenu) {
        this.estudianteService = estudianteService;
        this.vistaMenu = vistaMenu;
        this.buildCommands();
    }

    private void buildCommands() {
        commands = new LinkedHashMap<>();
        commands.put(1, new PromedioEdadEstudiantesContaduriaCommand(this.estudianteService));
        commands.put(2, new PorcentajeHombresIngenieriaCommand(this.estudianteService));
        commands.put(3, new PorcentajeMujeresMenores20Command(this.estudianteService));
        commands.put(4, new PromedioEdadMujeresIngenieriaCommand(this.estudianteService));
        commands.put(5, new PorcentajeHombresMayores22DerechoNocheCommand(this.estudianteService));
        commands.put(6, new SalirCommand());
    }

    public void start(){
        Map<Integer, String> labels = new LinkedHashMap<>();
        commands.forEach((c, command) -> labels.put(c,command.name()));

        vistaMenu.renderMenu(labels);

        commands.forEach((c, command) -> vistaMenu.bindOption(c,()->{
            CommandResult<?> commandResult = (CommandResult<?>) command.execute();
            vistaMenu.showMessage(commandResult.title(),commandResult.data());

            if(commandResult.exit()) vistaMenu.close();
        }));

        vistaMenu.show();
    }
}
