package org.example.controlador.services;

import org.example.controlador.DTO.ClienteDTO;
import org.example.controlador.mapper.ClienteMapper;
import org.example.modelo.Cliente;
import org.example.modelo.ColorCabello;
import org.example.modelo.ColorOjos;
import org.example.repositorios.ClienteRepository;

import java.util.List;

public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<ClienteDTO> obtenerReporteMujeres() {
        return clienteRepository.obtenerReporteMujeres().stream().map(ClienteMapper::toDTO).toList();
    }

    @Override
    public List<ClienteDTO> obtenerReporteHombres() {
        return clienteRepository.obtenerReporteHombres().stream().map(ClienteMapper::toDTO).toList();
    }
}
