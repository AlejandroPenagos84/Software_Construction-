package org.example.repositorios;

import org.example.modelo.Cliente;

import java.util.List;

public interface ClienteRepository {
    List<Cliente> obtenerReporteMujeres();
    List<Cliente> obtenerReporteHombres();
}
