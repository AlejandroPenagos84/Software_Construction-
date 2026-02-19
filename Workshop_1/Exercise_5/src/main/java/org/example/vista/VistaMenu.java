package org.example.vista;
import org.example.controlador.DTO.ArticuloRequestDTO;
import org.example.controlador.DTO.ArticuloResponseDTO;

import java.util.List;
import java.util.Map;

public interface VistaMenu {
    void renderMenu(Map<Integer, String> labels);
    void bindOption(int option, Runnable action);
    void showMessage(String title, Object message);
    void show();
    void close();
    void crearFormulario(String titulo,Runnable onConfirmar, String... campos);
    List<ArticuloRequestDTO> obtenerDatos();
}