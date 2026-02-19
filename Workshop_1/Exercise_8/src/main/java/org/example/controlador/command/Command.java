package org.example.controlador.command;

public interface Command <T> {
    String name();
    T execute();
}
