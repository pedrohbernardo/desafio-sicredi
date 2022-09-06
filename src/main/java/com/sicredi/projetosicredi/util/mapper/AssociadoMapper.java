package com.sicredi.projetosicredi.util.mapper;

import com.sicredi.projetosicredi.models.Associado;
import com.sicredi.projetosicredi.models.dto.AssociadoDTO;

public class AssociadoMapper {
    public static Associado toEntity(AssociadoDTO associadoDTO) {
        return Associado.builder()
                .cpf(associadoDTO.getCpf())
                .build();
    }
}
