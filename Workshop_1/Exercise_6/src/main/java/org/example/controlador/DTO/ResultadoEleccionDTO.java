package org.example.controlador.DTO;

import java.util.List;
import java.util.stream.Collectors;

public class ResultadoEleccionDTO {
    private String estado;        // "GANADOR", "SEGUNDA_VUELTA", "ANULADA"
    private String ganador;       // solo si estado = GANADOR
    private List<CandidatoDTO> segundaVuelta;  // solo si estado = SEGUNDA_VUELTA
    private String mensaje;       // siempre, para mostrar en la vista

    public ResultadoEleccionDTO(String estado, List<CandidatoDTO> segundaVuelta, String mensaje, String ganador) {
        this.estado = estado;
        this.segundaVuelta = segundaVuelta;
        this.mensaje = mensaje;
        this.ganador = ganador;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getGanador() {
        return ganador;
    }

    public void setGanador(String ganador) {
        this.ganador = ganador;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<CandidatoDTO> getSegundaVuelta() {
        return segundaVuelta;
    }

    public void setSegundaVuelta(List<CandidatoDTO> segundaVuelta) {
        this.segundaVuelta = segundaVuelta;
    }

    public List<String> getNombresSegundaVuelta() {
        return segundaVuelta.stream()
                .map(CandidatoDTO::getNombre)
                .collect(Collectors.toList());
    }
}
