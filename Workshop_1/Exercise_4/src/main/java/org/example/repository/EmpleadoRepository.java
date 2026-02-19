package org.example.repository;

import org.example.modelo.Empleado;

import java.util.List;

public interface EmpleadoRepository {
    List<Empleado> obtenerEmpleados();
}
