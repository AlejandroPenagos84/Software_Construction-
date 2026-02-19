package org.example.controlador.service;

import org.example.controlador.DTO.ArticuloResponseDTO;
import java.util.List;

public interface VentaService {
    Double calcularComision(List<ArticuloResponseDTO> articuloDTOS);
}
