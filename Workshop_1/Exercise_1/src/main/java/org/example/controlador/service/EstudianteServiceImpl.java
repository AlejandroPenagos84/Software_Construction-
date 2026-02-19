package org.example.controlador.service;

import org.example.controlador.service.EstudianteService;
import org.example.repository.EstudianteRepository;

public class EstudianteServiceImpl implements EstudianteService {
    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Double promedioEdadEstudiantesContaduria() {
        return this.estudianteRepository.promedioEdadEstudiantesContaduria();
    }

    @Override
    public Double porcentajeHombresIngenieria() {
        return this.estudianteRepository.porcentajeHombresIngenieria();
    }

    @Override
    public Double porcentajeMujeresMenores20() {
        return this.estudianteRepository.porcentajeMujeresMenores20();
    }

    @Override
    public Double  promedioEdadMujeresIngenieria() {
        return this.estudianteRepository.promedioEdadMujeresIngenieria();
    }

    @Override
    public Double porcentajeHombresMayores22DerechoNoche() {
        return this.estudianteRepository.porcentajeHombresMayores22DerechoNoche();
    }
}
