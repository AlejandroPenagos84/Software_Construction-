package org.example.controlador;

import org.example.controlador.ResponseCommand.PrimeraVueltaResponse;
import org.example.controlador.State.EstadoEleccion;
import org.example.controlador.State.EstadoPrimeraVuelta;
import org.example.controlador.service.CandidatoService;
import org.example.vista.VistaMenu;


public class MenuController {
    private final VistaMenu vistaMenu;
    private EstadoEleccion estadoActual;
    private CandidatoService candidatoService;

    public MenuController(CandidatoService candidatoService, VistaMenu vistaMenu) {
        this.vistaMenu = vistaMenu;
        this.candidatoService = candidatoService;

        this.estadoActual = new EstadoPrimeraVuelta(new PrimeraVueltaResponse(candidatoService));
    }

    public void setEstado(EstadoEleccion nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public void start() {
        refrescarVista();
        vistaMenu.show();
    }

    public void refrescarVista() {
        estadoActual.montarInterfaz(this, vistaMenu);
    }

    public CandidatoService getService() {
        return this.candidatoService;
    }
}
