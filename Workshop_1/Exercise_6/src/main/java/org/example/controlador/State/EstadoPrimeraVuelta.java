package org.example.controlador.State;

import org.example.controlador.ResponseCommand.CommandResult;
import org.example.controlador.ResponseCommand.ResponseCommand;
import org.example.controlador.ResponseCommand.SegundaVueltaResponse;
import org.example.controlador.DTO.ResultadoEleccionDTO;
import org.example.controlador.MenuController;
import org.example.vista.VistaMenu;

import java.util.List;
import java.util.Map;

public class EstadoPrimeraVuelta implements EstadoEleccion {
    private final ResponseCommand<ResultadoEleccionDTO> comandoPrimeraVuelta;
    public EstadoPrimeraVuelta(ResponseCommand<ResultadoEleccionDTO> comando) {
        this.comandoPrimeraVuelta = comando;
    }

    @Override
    public void montarInterfaz(MenuController controller, VistaMenu vista) {
        // El estado decide pintar el menú con la opción de primera vuelta
        vista.renderMenu(Map.of(1, "Iniciar Primera Vuelta", 3, "Salir"));

        vista.bindOption(1, () -> {
            vista.crearFormulario("Primera Vuelta", () -> {

                Map<String, String> datos = vista.obtenerDatos();

                CommandResult<ResultadoEleccionDTO> resultado = comandoPrimeraVuelta.execute(datos);

                ResultadoEleccionDTO dto = resultado.data();
                if ("SEGUNDA_VUELTA".equals(dto.getEstado())) {
                    vista.showMessage(resultado.title(), resultado.data().getEstado() + " Candidatos: " + resultado.data().getSegundaVuelta().getFirst() + resultado.data().getSegundaVuelta().get(1));
                    List<String> segundaVueltaNombres = dto.getNombresSegundaVuelta();
                    String c1 = segundaVueltaNombres.get(0);;
                    String c2 =segundaVueltaNombres.get(1);

                    controller.setEstado(new EstadoSegundaVuelta(c1, c2, new SegundaVueltaResponse(controller.getService())));
                    controller.refrescarVista();
                } else {
                    if("EMPATE".equals(dto.getEstado())){
                        vista.showMessage(resultado.title(), resultado.data().getEstado());
                    } else if("GANADOR".equals(dto.getEstado())){
                        vista.showMessage(resultado.title(), resultado.data().getEstado() + " Ganador: " + resultado.data().getGanador());
                    }else{
                        vista.showMessage(resultado.title(), resultado.data().getEstado());
                    }

                    controller.setEstado(new EstadoFinalizado(dto.getGanador()));
                    controller.refrescarVista();
                }

            }, "Juan", "Pedro", "Maria");
        });

        vista.bindOption(3, vista::close);
    }
}

