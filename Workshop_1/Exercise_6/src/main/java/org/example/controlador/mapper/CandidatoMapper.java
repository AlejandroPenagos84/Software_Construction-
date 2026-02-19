package org.example.controlador.mapper;

import org.example.controlador.DTO.CandidatoDTO;
import org.example.modelo.Candidato;

public class CandidatoMapper {

    public static CandidatoDTO toDTO(Candidato candidato) {
        if (candidato == null) {
            return null;
        }
        return new CandidatoDTO(candidato.getNombre(), candidato.getNumeroVotaciones());
    }

    public static Candidato toDomain(CandidatoDTO candidatoDTO){
        if (candidatoDTO == null) {
            return null;
        }
        return new Candidato(candidatoDTO.getNombre(), candidatoDTO.getNumeroVotaciones());
    }
}
