package org.example.controlador.ResponseCommand;

import org.example.controlador.DTO.ArticuloRequestDTO;

import java.util.List;

public interface ResponseCommand<T> {
    String name();
    CommandResult<T> execute(List<ArticuloRequestDTO> datos);
}