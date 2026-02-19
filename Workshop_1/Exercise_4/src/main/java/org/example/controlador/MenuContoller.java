package org.example.controlador;

import org.example.controlador.command.*;
import org.example.controlador.services.EmpleadoService;
import org.example.vista.VistaMenu;
import java.util.LinkedHashMap;
import java.util.Map;

public class MenuContoller {
    private final EmpleadoService empleadoService;
    private final VistaMenu vistaMenu;
    private Map<Integer, Command<?>> commands;

    public MenuContoller(EmpleadoService empleadoService, VistaMenu vistaMenu) {
        this.empleadoService = empleadoService;
        this.vistaMenu = vistaMenu;
        this.buildCommands();
    }

    private void buildCommands() {
        commands = new LinkedHashMap<>();
        commands.put(1, new ObtenerEmpleadosCommand(this.empleadoService));
        commands.put(2, new SalirCommand());
    }

    public void start() {
        Map<Integer, String> labels = new LinkedHashMap<>();
        commands.forEach((c, command) -> labels.put(c, command.name()));

        vistaMenu.renderMenu(labels);

        commands.forEach((c, command) -> vistaMenu.bindOption(c, () -> {
            CommandResult<?> commandResult = (CommandResult<?>) command.execute();
            vistaMenu.showMessage(commandResult.title(), commandResult.data());

            if (commandResult.exit()) vistaMenu.close();
        }));

        vistaMenu.show();
    }
}
