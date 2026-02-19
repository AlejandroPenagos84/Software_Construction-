package org.example.controlador.service;

import org.example.controlador.DTO.CandidatoDTO;
import org.example.controlador.DTO.ResultadoEleccionDTO;

import java.util.List;


public interface CandidatoService {
    ResultadoEleccionDTO calcularPrimeraVuelta(List<CandidatoDTO> candidatoDTOS);
    ResultadoEleccionDTO calcularSegundaVuelta(List<CandidatoDTO> candidatoDTOS);
}
