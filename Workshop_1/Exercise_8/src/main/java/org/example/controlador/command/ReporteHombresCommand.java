package org.example.controlador.command;

import org.example.controlador.DTO.ClienteDTO;
import org.example.controlador.services.ClienteService;

import java.util.List;

public class ReporteHombresCommand implements Command<CommandResult<List<ClienteDTO>>>{
    private final ClienteService clienteService;

    public ReporteHombresCommand(ClienteService clienteService){this.clienteService = clienteService;}

    @Override
    public String name() {
        return "Reporte Hombres";
    }

    @Override
    public CommandResult<List<ClienteDTO>> execute() {
        List<ClienteDTO> clientes = clienteService.obtenerReporteHombres();
        return new CommandResult<>("Reporte Hombres", clientes, false);
    }
}
