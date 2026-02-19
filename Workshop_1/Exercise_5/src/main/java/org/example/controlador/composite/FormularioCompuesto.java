package org.example.controlador.composite;

import java.util.ArrayList;
import java.util.List;

public class FormularioCompuesto {
    private final List<ComponentForm> campos = new ArrayList<>();

    public void agregarCampo(String label) {
        campos.add(new CampoTexto(label + " "+ campos.size()));
    }

    public void eliminarCampo() {
        if (campos.size() > 1) campos.removeLast();
    }

    public void reiniciar() {
        campos.clear();
        agregarCampo("Articulo"); // Dejar al menos uno por defecto
    }
    public String[] obtenerLabels() {
        return campos.stream()
                .map(ComponentForm::getLabel)
                .toArray(String[]::new);
    }
}
