package org.example.repository;

import org.example.modelo.Empleado;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class EmpleadoRepositoryProperties implements EmpleadoRepository {
    private final Properties prop = new Properties();

    public EmpleadoRepositoryProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("Empleados.properties")) {

            if (input == null) {
                throw new RuntimeException("Archivo no encontrado");
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando datos", e);
        }
    }

    @Override
    public List<Empleado> obtenerEmpleados() {
        List<Empleado> empleados = new ArrayList<>();

        Set<String> ids = prop.stringPropertyNames()
                .stream().map(c -> c.split("\\.")[1]).collect(Collectors.toSet());
        
        for (String id : ids) {
            Empleado e = this.leerEmpleado(id);
            empleados.add(e);
        }

        return empleados;
    }

    private Empleado leerEmpleado(String id) {
        String prefix = "empleado." + id + ".";

        String codigo = prop.getProperty(prefix + "codigo");
        String nombre = prop.getProperty(prefix + "nombre");
        Double salarioHora = Double.parseDouble(prop.getProperty(prefix + "salarioHora"));
        Integer numHoras = Integer.parseInt(prop.getProperty(prefix + "numHoras"));
        Integer numHijos = Integer.parseInt(prop.getProperty(prefix + "numHijos"));

        Empleado empleado = new Empleado(codigo, nombre, salarioHora, numHijos, numHoras);
        
        empleado.calcularDevengado();
        empleado.calcularSubsidio();
        empleado.calcularRetencion(numHijos, empleado.getDevengado());
        empleado.calcularSalarioNeto();
        
        return empleado;
    }
}
