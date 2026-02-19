package org.example.controlador.State;

import org.example.controlador.MenuController;
import org.example.vista.VistaMenu;

public interface EstadoEleccion {
    void montarInterfaz(MenuController controller, VistaMenu vista);
}