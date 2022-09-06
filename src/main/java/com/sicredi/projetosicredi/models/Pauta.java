package com.sicredi.projetosicredi.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nomePauta;

    @Column
    private Long tempoDuracaoPauta;

    @Column
    private LocalDateTime tempoFinalPauta;

    private Boolean statusPauta = false;

    public Pauta(Long tempoPauta) {
        this.tempoDuracaoPauta = tempoPauta;
    }
}
