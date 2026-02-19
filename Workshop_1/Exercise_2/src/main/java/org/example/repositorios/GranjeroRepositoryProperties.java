package org.example.repositorios;

import org.example.modelo.Granjero;
import org.example.modelo.TipoFumigacion;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class GranjeroRepositoryProperties implements GranjeroRepository {
    private final Properties prop = new Properties();

    public GranjeroRepositoryProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("Granjeros.properties")) {

            if (input == null) {
                throw new RuntimeException("Archivo Granjeros.properties no encontrado");
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando datos de granjeros", e);
        }
    }

    @Override
    public List<Granjero> obtenerTodos() {
        List<Granjero> granjeros = new ArrayList<>();

        Set<String> ids = prop.stringPropertyNames()
                .stream()
                .map(key -> key.split("\\.")[1])
                .collect(Collectors.toSet());

        for (String id : ids) {
            granjeros.add(leerGranjero(id));
        }

        return granjeros;
    }

    private Granjero leerGranjero(String id) {
        String prefix = "granjero." + id + ".";

        String nombre = prop.getProperty(prefix + "nombre");
        Double numHectareas = Double.parseDouble(prop.getProperty(prefix + "numHectareas"));
        int codigoTipo = Integer.parseInt(prop.getProperty(prefix + "tipoFumigacion"));
        TipoFumigacion tipo = TipoFumigacion.fromCodigo(codigoTipo);

        return new Granjero(nombre, numHectareas, tipo);
    }
}
