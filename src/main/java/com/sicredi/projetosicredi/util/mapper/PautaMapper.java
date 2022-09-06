package com.sicredi.projetosicredi.util.mapper;

import com.sicredi.projetosicredi.models.Pauta;
import com.sicredi.projetosicredi.models.dto.PautaDTO;

public class PautaMapper {
    public static Pauta toEntity(PautaDTO pautaDTO) {
        return Pauta.builder()
                .nomePauta(pautaDTO.getNomePauta())
                .tempoDuracaoPauta(pautaDTO.getTempoDuracaoPauta() != null ? pautaDTO.getTempoDuracaoPauta() : 1L)
                .statusPauta(Boolean.FALSE)
                .build();
    }
}
