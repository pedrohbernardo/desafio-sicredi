package com.sicredi.projetosicredi.models.dto;

import com.sicredi.projetosicredi.util.Constants;
import lombok.Data;

@Data
public class VotoDTO {

    private Long idPauta;

    private String cpfAssociado;

    private String voto;

    public Boolean getVoto() {
        return this.voto.equalsIgnoreCase(Constants.SIM) ? Boolean.TRUE : Boolean.FALSE;
    }
}
