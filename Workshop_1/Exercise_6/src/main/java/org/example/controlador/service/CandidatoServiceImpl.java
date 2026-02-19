package org.example.controlador.service;

import org.example.controlador.DTO.CandidatoDTO;
import org.example.controlador.DTO.ResultadoEleccionDTO;
import org.example.controlador.mapper.CandidatoMapper;
import org.example.modelo.Candidato;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CandidatoServiceImpl implements CandidatoService{
    public CandidatoServiceImpl() {}

    @Override
    public ResultadoEleccionDTO calcularPrimeraVuelta(List<CandidatoDTO> candidatoDTOS) {
        System.out.println(candidatoDTOS.get(2).getNombre());

        Candidato juan = CandidatoMapper.toDomain(candidatoDTOS.stream().filter(c -> c.getNombre().trim().contains("Juan")).findFirst().orElseThrow());
        Candidato pedro = CandidatoMapper.toDomain(candidatoDTOS.stream().filter(c -> c.getNombre().trim().contains("Pedro")).findFirst().orElseThrow());
        Candidato maria = CandidatoMapper.toDomain(candidatoDTOS.stream().filter(c -> c.getNombre().trim().contains("Maria")).findFirst().orElseThrow());

        int total = juan.getNumeroVotaciones() + pedro.getNumeroVotaciones() + maria.getNumeroVotaciones();
        int minParaGanar = (total / 2) + 1;

        if (juan.getNumeroVotaciones() >= minParaGanar)
            return new ResultadoEleccionDTO("GANADOR", List.of(CandidatoMapper.toDTO(juan)), null, juan.getNombre() + " gana en primera vuelta");
        if (pedro.getNumeroVotaciones() >= minParaGanar)
            return new ResultadoEleccionDTO("GANADOR", List.of(CandidatoMapper.toDTO(pedro)), null, pedro.getNombre() + " gana en primera vuelta");
        if (maria.getNumeroVotaciones() >= minParaGanar)
            return new ResultadoEleccionDTO("GANADOR", List.of(CandidatoMapper.toDTO(maria)), null, maria.getNombre() + " gana en primera vuelta");

        if (juan.getNumeroVotaciones().equals(pedro.getNumeroVotaciones()) &&
                pedro.getNumeroVotaciones().equals(maria.getNumeroVotaciones()))
            return new ResultadoEleccionDTO("ANULADA", null, null, "Empate triple - elección anulada");

        List<Candidato> ordenados = Stream.of(juan, pedro, maria)
                .sorted((a, b) -> b.getNumeroVotaciones() - a.getNumeroVotaciones())
                .toList();

        if (ordenados.get(1).getNumeroVotaciones().equals(ordenados.get(2).getNumeroVotaciones()))
            return new ResultadoEleccionDTO("ANULADA", null, null, "Empate en segundo lugar - elección anulada");

        List<String> segundaVuelta = List.of(ordenados.get(0).getNombre(), ordenados.get(1).getNombre());
        List<CandidatoDTO> segundaVueltaDTO = List.of(CandidatoMapper.toDTO(ordenados.get(0)), CandidatoMapper.toDTO(ordenados.get(1)));

        return new ResultadoEleccionDTO("SEGUNDA_VUELTA", segundaVueltaDTO,
                "Segunda vuelta entre: " + segundaVuelta.get(0) + " y " + segundaVuelta.get(1),null);
    }

    @Override
    public ResultadoEleccionDTO calcularSegundaVuelta(List<CandidatoDTO> candidatoDTOS) {

        CandidatoDTO candidato1 = candidatoDTOS.get(0);
        CandidatoDTO candidato2 = candidatoDTOS.get(1);

        if (candidato1.getNumeroVotaciones() > candidato2.getNumeroVotaciones()) {
            return new ResultadoEleccionDTO("GANADOR", null, candidato1.getNombre() + " gana en segunda vuelta", candidato1.getNombre());
        } else if (candidato2.getNumeroVotaciones() > candidato1.getNumeroVotaciones()) {
            return new ResultadoEleccionDTO("GANADOR", null, candidato2.getNombre() + " gana en segunda vuelta", candidato2.getNombre());
        } else {
            return new ResultadoEleccionDTO("ANULADA", null, "Empate en segunda vuelta - elección anulada", null);
        }
    }
}
