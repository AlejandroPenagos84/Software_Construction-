package org.example.controlador.State;

import org.example.controlador.DTO.ArticuloRequestDTO;
import org.example.controlador.ResponseCommand.CommandResult;
import org.example.controlador.ResponseCommand.ResponseCommand;
import org.example.controlador.composite.FormularioCompuesto;
import org.example.controlador.MenuController;
import org.example.vista.VistaMenu;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EstadoConfiguracionDinamica implements EstadoVenta{
    private final FormularioCompuesto formBuilder = new FormularioCompuesto();
    private final ResponseCommand<Double> obtenerComisionCommand;

    public EstadoConfiguracionDinamica(ResponseCommand<Double> obtenerComisionCommand){
        this.obtenerComisionCommand = obtenerComisionCommand;
        formBuilder.agregarCampo("Articulo");
    }
    @Override
    public void render(MenuController controller, VistaMenu vistaMenu) {
        // 2. Definimos el menú de control
        Map<Integer, String> opcionesMenu = new LinkedHashMap<>();
        opcionesMenu.put(0,"Iniciar Cálculo de Comisión");
        opcionesMenu.put(1, "Añadir Campo (+)");
        opcionesMenu.put(2, "Quitar Campo (-)");
        opcionesMenu.put(3, "Salir");

        vistaMenu.renderMenu(opcionesMenu);

        vistaMenu.crearFormulario("Articulos",()->{
            List<ArticuloRequestDTO> datos = vistaMenu.obtenerDatos();
            CommandResult<Double> resultado = obtenerComisionCommand.execute(datos);
            vistaMenu.showMessage(resultado.title(), "Comisión Total: " + resultado.data());

        }, formBuilder.obtenerLabels());

        vistaMenu.bindOption(0, () -> {
            formBuilder.reiniciar();
            controller.refrescarVista();
        });

        vistaMenu.bindOption(1, () -> {
            formBuilder.agregarCampo("Articulo");
            controller.refrescarVista();
        });

        vistaMenu.bindOption(2, () -> {
            formBuilder.eliminarCampo();
            controller.refrescarVista();
        });

        vistaMenu.bindOption(3, vistaMenu::close);
    }
}
