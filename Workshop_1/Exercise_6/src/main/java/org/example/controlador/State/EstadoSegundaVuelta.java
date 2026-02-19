package org.example.controlador.State;

import org.example.controlador.ResponseCommand.CommandResult;
import org.example.controlador.ResponseCommand.PrimeraVueltaResponse;
import org.example.controlador.ResponseCommand.ResponseCommand;
import org.example.controlador.DTO.ResultadoEleccionDTO;
import org.example.controlador.MenuController;
import org.example.vista.VistaMenu;

import java.util.Map;

public class EstadoSegundaVuelta implements EstadoEleccion {
    private final String c1;
    private final String c2;
    private final ResponseCommand<ResultadoEleccionDTO> comandoSegundaVuelta;

    public EstadoSegundaVuelta(String c1, String c2, ResponseCommand<ResultadoEleccionDTO> comando) {
        this.c1 = c1;
        this.c2 = c2;
        this.comandoSegundaVuelta = comando;
    }


    @Override
    public void montarInterfaz(MenuController controller, VistaMenu vista) {
        vista.renderMenu(Map.of(
                1, "Registrar Segunda Vuelta (" + c1 + " vs " + c2 + ")",
                2, "Reiniciar Proceso",
                3, "Salir"
        ));

        // Opción 1: El Formulario Dinámico
        vista.bindOption(1, () -> {
            vista.crearFormulario("Votación de Desempate", () -> {
                Map<String, String> datos = vista.obtenerDatos();

                CommandResult<ResultadoEleccionDTO> resultado = comandoSegundaVuelta.execute(datos);

                vista.showMessage(resultado.title(), resultado.data().getEstado() + " " + resultado.data().getGanador());

                controller.setEstado(new EstadoFinalizado(resultado.data().getGanador()));
                controller.refrescarVista();

            }, c1, c2);
        });

        vista.bindOption(2, () -> {
            controller.setEstado(new EstadoPrimeraVuelta(new PrimeraVueltaResponse(controller.getService())));
            controller.refrescarVista();
        });

        vista.bindOption(3, vista::close);
    }
}