package org.example.controlador.command;

public class SalirCommand implements Command<CommandResult<String>>{
    @Override
    public String name() {
        return "Salir";
    }

    @Override
    public CommandResult<String> execute() {
        return new CommandResult<>("Sistema","Saliendo del sistema", true);
    }
}
