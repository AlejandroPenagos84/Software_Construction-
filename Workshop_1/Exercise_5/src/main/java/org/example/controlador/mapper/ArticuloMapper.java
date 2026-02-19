package org.example.controlador.mapper;

import org.example.controlador.DTO.ArticuloResponseDTO;
import org.example.modelo.Articulo;

public class ArticuloMapper {
    public static Articulo toDomain(ArticuloResponseDTO articuloDTO){
        return new Articulo(articuloDTO.getNombre(), articuloDTO.getPrecio(), articuloDTO.getCantidad());
    }
}
