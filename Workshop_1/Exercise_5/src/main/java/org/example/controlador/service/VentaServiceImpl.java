package org.example.controlador.service;

import org.example.controlador.DTO.ArticuloResponseDTO;
import org.example.controlador.mapper.ArticuloMapper;
import org.example.modelo.Articulo;
import org.example.modelo.Venta;

import java.util.List;

public class VentaServiceImpl implements VentaService{
    @Override
    public Double calcularComision(List<ArticuloResponseDTO> articuloDTOS) {
        List<Articulo> articulos = articuloDTOS.stream().
                map(ArticuloMapper::toDomain).toList();
        Venta venta = new Venta(articulos);
        return venta.calcularComision();
    }
}
