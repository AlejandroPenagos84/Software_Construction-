package org.example.controlador;

import org.example.controlador.DTO.EmpleadoRequestDTO;
import org.example.controlador.DTO.EmpleadoResponseDTO;
import org.example.controlador.command.AgregarEmpleadoCommand;
import org.example.controlador.command.CommandResult;
import org.example.controlador.command.ResponseCommand;
import org.example.controlador.service.EmpleadoService;
import org.example.vista.VistaMenu;

import java.util.Map;

public class MenuController {
    private final VistaMenu vistaMenu;
    private final EmpleadoService empleadoService;
    private ResponseCommand<EmpleadoResponseDTO> agregarEmpleadoCommand;

    public MenuController(EmpleadoService empleadoService, VistaMenu vistaMenu) {
        this.vistaMenu = vistaMenu;
        this.empleadoService = empleadoService;
        this.agregarEmpleadoCommand = new AgregarEmpleadoCommand(this.empleadoService);
    }

    public void start() {
        vistaMenu.renderMenu(Map.of(
                1, "Agregar Empleado",
                2, "Salir"
        ));

        vistaMenu.bindOption(1, this::crearForm);
        vistaMenu.bindOption(2, vistaMenu::close);

        vistaMenu.show();
    }

    private void crearForm() {
        vistaMenu.crearFormulario("Agregar Empleado", () -> {
            EmpleadoRequestDTO datos = vistaMenu.obtenerDatos();
            CommandResult<EmpleadoResponseDTO> resultado = agregarEmpleadoCommand.execute(datos);
            EmpleadoResponseDTO dto = resultado.data();

            vistaMenu.showMessage(resultado.title(),
                    "Código: "          + dto.getCodigo()        + "\n" +
                            "Nombre: "          + dto.getNombre()         + "\n" +
                            "Sexo: "            + dto.getSexo()           + "\n" +
                            "Salario bruto: $"  + dto.getSalarioBruto()   + "\n" +
                            "Retención: $"      + dto.getRetencion()      + "\n" +
                            "Salario neto: $"   + dto.getSalarioNeto()
            );

        });
    }
}
