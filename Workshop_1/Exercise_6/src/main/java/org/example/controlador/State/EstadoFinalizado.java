package org.example.controlador.State;

import org.example.controlador.ResponseCommand.PrimeraVueltaResponse;
import org.example.controlador.MenuController;
import org.example.vista.VistaMenu;

import java.util.Map;

public class EstadoFinalizado implements EstadoEleccion {
    private final String ganador;

    public EstadoFinalizado(String ganador) {
        this.ganador = ganador;
    }


    @Override
    public void montarInterfaz(MenuController controller, VistaMenu vista) {
        // En este estado, el menú solo ofrece opciones de cierre o reinicio
        vista.renderMenu(Map.of(
                1, "Ver Acta de Proclamación",
                2, "Nueva Elección (Reiniciar)",
                3, "Cerrar Aplicación"
        ));

        vista.bindOption(1, () -> {
            String mensaje = "Por medio de la presente se proclama a: \n\n" +
                    ganador.toUpperCase() +
                    "\n\ncomo el candidato electo.";
            vista.showMessage("Acta de Resultados", mensaje);
        });

        vista.bindOption(2, () -> {
            controller.setEstado(new EstadoPrimeraVuelta(new PrimeraVueltaResponse(controller.getService())));
            controller.refrescarVista();
        });

        vista.bindOption(3, () -> vista.close());
    }
}