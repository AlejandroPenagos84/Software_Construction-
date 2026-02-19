package org.example.repositorios;

import org.example.modelo.Cliente;
import org.example.modelo.ColorCabello;
import org.example.modelo.ColorOjos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class ClienteRepositoryProperties implements ClienteRepository{
    private final Properties prop = new Properties();

    public ClienteRepositoryProperties() {
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("Data.properties")) {

            if (input == null) {
                throw new RuntimeException("Archivo no encontrado");
            }

            prop.load(input);

        } catch (IOException e) {
            throw new RuntimeException("Error cargando datos", e);
        }
    }

    @Override
    public List<Cliente> obtenerReporteMujeres() {
        List<Cliente> clientes = new ArrayList<>();

        Set<String> ids = prop.stringPropertyNames()
                .stream().map(c -> c.split("\\.")[1]).collect(Collectors.toSet());
        for (String id : ids) {
            Cliente c = this.leerCLiente(id);
            if (c.getSexo() == 'F'
                    && c.getAltura() >= 1.65 && c.getAltura() <= 1.75
                    && c.getColorCabello()== ColorCabello.RUBIO
                    && c.getColorOjos() == ColorOjos.AZULES
                    && c.getPeso() <= 120){
                clientes.add(c);
            }
        }
        return clientes;
    }

    @Override
    public List<Cliente> obtenerReporteHombres() {
        List<Cliente> clientes = new ArrayList<>();

        Set<String> ids = prop.stringPropertyNames()
                .stream().map(c -> c.split("\\.")[1]).collect(Collectors.toSet());
        for (String id : ids) {
            Cliente c = this.leerCLiente(id);
            if (c.getSexo() == 'M'
                    && c.getAltura() >= 1.70
                    && c.getColorOjos() == ColorOjos.CASTAÑO
                    && c.getPeso() >= 180 && c.getPeso() <= 220){
                System.out.println("xd2");
                clientes.add(c);
            }
        }

        return clientes;
    }

    private Cliente leerCLiente(String id){
        String prefix = "cliente." + id + ".";

        String nombre = prop.getProperty(prefix + "nombre");
        Character sexo = prop.getProperty(prefix + "sexo").charAt(0);
        Integer edad = Integer.parseInt(prop.getProperty(prefix + "edad"));
        Double altura = Double.parseDouble(prop.getProperty(prefix + "altura"));
        Double peso = Double.parseDouble(prop.getProperty(prefix + "peso"));

        int codigoOjos = Integer.parseInt(prop.getProperty(prefix + "colorOjos"));
        int codigoCabello = Integer.parseInt(prop.getProperty(prefix + "colorCabello"));

        ColorOjos colorOjos = ColorOjos.fromCodigo(codigoOjos);
        ColorCabello colorCabello = ColorCabello.fromCodigo(codigoCabello);

        return new Cliente(nombre, sexo, edad, altura, peso, colorOjos, colorCabello);
    }
}
