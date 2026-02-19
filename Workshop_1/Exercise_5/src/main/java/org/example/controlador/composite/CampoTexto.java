package org.example.controlador.composite;

public record CampoTexto(String label) implements ComponentForm {
    public String getLabel() { return label; }
}
