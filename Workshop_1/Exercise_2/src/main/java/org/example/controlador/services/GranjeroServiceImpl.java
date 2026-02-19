package org.example.controlador.services;

import org.example.controlador.DTO.GranjeroDTO;
import org.example.controlador.mapper.GranjeroMapper;
import org.example.modelo.Granjero;
import org.example.repositorios.GranjeroRepository;

import java.util.List;

public class GranjeroServiceImpl implements GranjeroService {

    private final GranjeroRepository granjeroRepository;

    public GranjeroServiceImpl(GranjeroRepository granjeroRepository) {
        this.granjeroRepository = granjeroRepository;
    }

    @Override
    public List<GranjeroDTO> calcularFumigacion() {
        List<Granjero> granjeros = granjeroRepository.obtenerTodos();
        return granjeros.stream()
                .map(g -> GranjeroMapper.toDTO(g, calcularValor(g)))
                .toList();
    }

    /**
     * Reglas de negocio:
     * 1. subtotal = numHectareas * precioPorHectarea
     * 2. Si hectareas > 1000 → descuento 5% (se aplica primero)
     * 3. Si subtotal (post-descuento superficie) > 3000 → descuento 10% sobre el
     * exceso
     */
    private double calcularValor(Granjero granjero) {
        double subtotal = granjero.getNumHectareas() * granjero.getTipoFumigacion().getPrecioPorHectarea();

        // Descuento por superficie (primero)
        if (granjero.getNumHectareas() > 1000) {
            subtotal *= 0.95;
        }

        // Descuento por cuenta que excede $3000
        if (subtotal > 3000) {
            double exceso = subtotal - 3000;
            subtotal -= exceso * 0.10;
        }

        return subtotal;
    }
}
