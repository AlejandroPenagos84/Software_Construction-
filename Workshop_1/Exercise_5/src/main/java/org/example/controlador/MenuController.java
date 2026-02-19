package org.example.controlador;

import org.example.controlador.ResponseCommand.ObtenerComisionCommand;
import org.example.controlador.State.EstadoConfiguracionDinamica;
import org.example.controlador.State.EstadoVenta;
import org.example.controlador.service.VentaService;
import org.example.vista.VistaMenu;

public class MenuController {
    private final VistaMenu vistaMenu;
    private EstadoVenta estadoActual;
    private VentaService ventaService;

    public MenuController(VistaMenu vistaMenu, VentaService ventaService) {
        this.ventaService = ventaService;
        this.vistaMenu = vistaMenu;
        this.estadoActual = new EstadoConfiguracionDinamica(new ObtenerComisionCommand(ventaService));
    }

    public void setEstado(EstadoVenta nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void start() {
        refrescarVista();
        vistaMenu.show();
    }

    public void refrescarVista() {
        estadoActual.render(this, vistaMenu);
    }
}
