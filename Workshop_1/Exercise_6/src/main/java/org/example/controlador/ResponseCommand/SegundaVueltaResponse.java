package org.example.controlador.ResponseCommand;

import org.example.controlador.DTO.CandidatoDTO;
import org.example.controlador.DTO.ResultadoEleccionDTO;
import org.example.controlador.service.CandidatoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SegundaVueltaResponse implements ResponseCommand<ResultadoEleccionDTO> {
    private final CandidatoService candidatoService;


    public SegundaVueltaResponse(CandidatoService candidatoService) {
        this.candidatoService = candidatoService;
    }

    @Override
    public String name() {
        return "Segunda Vuelta";
    }

    @Override
    public CommandResult<ResultadoEleccionDTO> execute(Map<String, String> datos) {
        List<CandidatoDTO> candidatoDTOList = new ArrayList<>();
        for(Map.Entry<String, String> entry : datos.entrySet()){
            candidatoDTOList.add(new CandidatoDTO(entry.getKey(),Integer.parseInt(entry.getValue())));
        }

        ResultadoEleccionDTO resultadoEleccionDTO = this.candidatoService.calcularSegundaVuelta(candidatoDTOList);
        return new CommandResult<>("Resultado de la Primera Vuelta", resultadoEleccionDTO, false);
    }
}
