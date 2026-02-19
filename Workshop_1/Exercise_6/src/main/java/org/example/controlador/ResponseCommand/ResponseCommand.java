package org.example.controlador.ResponseCommand;

import java.util.Map;

public interface ResponseCommand<T> {
    String name();
    CommandResult<T> execute(Map<String, String> datos);
}