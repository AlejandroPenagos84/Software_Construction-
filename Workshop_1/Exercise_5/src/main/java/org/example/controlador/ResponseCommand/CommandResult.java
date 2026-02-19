package org.example.controlador.ResponseCommand;

public record CommandResult<T>(String title, T data, boolean exit) {}