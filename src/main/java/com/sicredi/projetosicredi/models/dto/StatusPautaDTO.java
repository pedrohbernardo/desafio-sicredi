package com.sicredi.projetosicredi.models.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatusPautaDTO {

    private String nomePauta;

    private Long quantidadeVotosSim;

    private Long quantidadeVotosNao;
}
