package org.example.controlador.mapper;

import org.example.controlador.DTO.EmpleadoRequestDTO;
import org.example.controlador.DTO.EmpleadoResponseDTO;
import org.example.modelo.Empleado;

public class EmpleadoMapper {

    public static EmpleadoResponseDTO toDTO(Empleado empleado) {
        return new EmpleadoResponseDTO(
                empleado.getCodigo(),
                empleado.getNombre(),
                empleado.getSexo(),
                empleado.getSalarioBruto(),
                empleado.getSalarioNeto(),
                empleado.getRetencion()
        );
    }

    public static Empleado toModel(EmpleadoRequestDTO request) {
        Empleado empleado = new Empleado(
                request.getCodigo(),
                request.getNombre(),
                request.getSexo(),
                request.getNumHoras()
        );

        empleado.setNumHorasExtra();
        empleado.setSalarioBruto(request.getTarifaHora());
        empleado.setImpuestos();
        empleado.setRetencion();
        empleado.setSalarioNeto();

        return empleado;
    }
}