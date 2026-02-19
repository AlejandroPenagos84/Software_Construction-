package org.example.controlador.services;

import org.example.controlador.DTO.ClienteDTO;
import org.example.modelo.Cliente;

import java.util.List;

public interface ClienteService {
    List<ClienteDTO> obtenerReporteMujeres();
    List<ClienteDTO> obtenerReporteHombres();
}
