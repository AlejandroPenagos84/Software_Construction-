package org.example.controlador.command;

public record CommandResult<T>(String title, T data, boolean exit) {
}
