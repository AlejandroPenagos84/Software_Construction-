package org.example.controlador.command;

import org.example.controlador.DTO.ClienteDTO;
import org.example.controlador.services.ClienteService;

import java.util.List;

public class ReporteMujeresCommand implements Command<CommandResult<List<ClienteDTO>>>{
    private final ClienteService clienteService;

    public ReporteMujeresCommand(ClienteService clienteService){this.clienteService = clienteService;}

    @Override
    public String name() {
        return "Reporte Mujeres";
    }

    @Override
    public CommandResult<List<ClienteDTO>> execute() {
        List<ClienteDTO> clientes = clienteService.obtenerReporteMujeres();
        return new CommandResult<>("Reporte Mujeres", clientes, false);
    }
}