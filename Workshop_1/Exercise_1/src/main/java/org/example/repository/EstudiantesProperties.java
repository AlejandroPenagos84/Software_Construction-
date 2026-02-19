package org.example.repository;

import org.example.modelo.Carrera;
import org.example.modelo.Estudiante;
import org.example.modelo.Jornada;
import org.example.modelo.Sexo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class EstudiantesProperties implements EstudianteRepository{
    private final Properties prop = new Properties();
    private Set<String> ids;

    public EstudiantesProperties(){
        try (InputStream input = getClass().getClassLoader()
                .getResourceAsStream("Data.properties")) {

            if (input == null) {
                throw new RuntimeException("Archivo no encontrado");
            }

            prop.load(input);

            ids = prop.stringPropertyNames()
                    .stream().map(c -> c.split("\\.")[1]).collect(Collectors.toSet());

        } catch (IOException e) {
            throw new RuntimeException("Error cargando datos", e);
        }
    }

    private Estudiante crearEstudianteDesdeId(String id) {
        String prefix = "estudiante." + id + ".";

        int codigoCarrera = Integer.parseInt(prop.getProperty(prefix + "carrera"));
        int codigoJornada = Integer.parseInt(prop.getProperty(prefix + "jornada"));
        int codigoSexo = Integer.parseInt(prop.getProperty(prefix + "sexo"));
        int edad = Integer.parseInt(prop.getProperty(prefix + "edad"));

        Carrera carrera = Carrera.fromCodigo(codigoCarrera);
        Jornada jornada = Jornada.fromCodigo(codigoJornada);
        Sexo sexo = Sexo.fromCodigo(codigoSexo);

        return new Estudiante(carrera, jornada, sexo, edad);
    }


    @Override
    public double promedioEdadEstudiantesContaduria() {
        int edadCont = 0;
        int cantContEstudiantes = 0;
        for(String id: ids){
            String prefix = "estudiante." + id + ".";

            int codigoCarrera = Integer.parseInt(prop.getProperty(prefix + "carrera"));
            Carrera carrera = Carrera.fromCodigo(codigoCarrera);

            if(carrera.equals(Carrera.CONTADURIA)) {
                int edad = Integer.parseInt(prop.getProperty(prefix + "edad"));
                edadCont+= edad;
                cantContEstudiantes++;
            }
        }

        return cantContEstudiantes > 0 ? ((double) edadCont / cantContEstudiantes) : 0;
    }

    @Override
    public double porcentajeHombresIngenieria() {
        int cantHombreInges = 0;
        int cantTotalEstudiantes = ids.size();

        for(String id: ids) {
            String prefix = "estudiante." + id + ".";

            int codigoSexo = Integer.parseInt(prop.getProperty(prefix + "sexo"));
            Sexo sexo = Sexo.fromCodigo(codigoSexo);

            if (sexo.equals(Sexo.MASCULINO)) {
                int codigoCarrera = Integer.parseInt(prop.getProperty(prefix + "carrera"));
                Carrera carrera = Carrera.fromCodigo(codigoCarrera);

                if (carrera.equals(Carrera.INGENIERIA)) {
                    cantHombreInges++;
                }
            }
        }

        return cantTotalEstudiantes > 0 ? ((double) cantHombreInges / cantTotalEstudiantes) * 100 : 0;
    }

    @Override
    public double porcentajeMujeresMenores20() {
        int cantMujeresMenores = 0;
        int cantMujeresTotal = 0;

        for(String id: ids) {
            String prefix = "estudiante." + id + ".";

            int codigoSexo = Integer.parseInt(prop.getProperty(prefix + "sexo"));
            Sexo sexo = Sexo.fromCodigo(codigoSexo);

            if (sexo.equals(Sexo.FEMENINO)) {
                int edad = Integer.parseInt(prop.getProperty(prefix + "edad"));
                cantMujeresTotal++;

                if (edad < 20) cantMujeresMenores++;

            }
        }

        return cantMujeresTotal > 0 ? ((double) cantMujeresMenores / cantMujeresTotal) * 100 : 0;
    }

    @Override
    public double promedioEdadMujeresIngenieria() {
        int edadCont = 0;
        int cantContMujeresIng = 0;
        for(String id: ids){
            String prefix = "estudiante." + id + ".";

            int codigoCarrera = Integer.parseInt(prop.getProperty(prefix + "carrera"));
            Carrera carrera = Carrera.fromCodigo(codigoCarrera);

            int codigoSexo = Integer.parseInt(prop.getProperty(prefix + "sexo"));
            Sexo sexo = Sexo.fromCodigo(codigoSexo);

            if(carrera.equals(Carrera.INGENIERIA) && sexo.equals(Sexo.FEMENINO)) {
                int edad = Integer.parseInt(prop.getProperty(prefix + "edad"));
                edadCont+= edad;
                cantContMujeresIng++;
            }
        }

        return cantContMujeresIng > 0 ? ((double) edadCont / cantContMujeresIng) : 0;
    }

    @Override
    public double porcentajeHombresMayores22DerechoNoche() {
        int cantHombreEspec = 0;
        int cantTotalEstudiantes = ids.size();

        for(String id: ids){
            String prefix = "estudiante." + id + ".";

            int codigoCarrera = Integer.parseInt(prop.getProperty(prefix + "carrera"));
            Carrera carrera = Carrera.fromCodigo(codigoCarrera);

            int codigoSexo = Integer.parseInt(prop.getProperty(prefix + "sexo"));
            Sexo sexo = Sexo.fromCodigo(codigoSexo);

            if(sexo.equals(Sexo.MASCULINO)) {
                int edad = Integer.parseInt(prop.getProperty(prefix + "edad"));
                int codigoJornada = Integer.parseInt(prop.getProperty(prefix + "jornada"));
                Jornada jornada = Jornada.fromCodigo(codigoJornada);

                if (carrera.equals(Carrera.DERECHO) && jornada.equals(Jornada.NOCTURNA) && edad > 22) {
                    cantHombreEspec++;
                }
            }
        }

        return cantTotalEstudiantes > 0 ? ((double) cantHombreEspec / cantTotalEstudiantes) * 100 : 0;
    }
}
