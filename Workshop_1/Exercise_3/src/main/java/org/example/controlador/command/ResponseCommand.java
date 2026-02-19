package org.example.controlador.command;

import org.example.controlador.DTO.EmpleadoRequestDTO;

import java.util.Map;

public interface ResponseCommand<T> {
    String name();
    CommandResult<T> execute(EmpleadoRequestDTO requestDTO);
}