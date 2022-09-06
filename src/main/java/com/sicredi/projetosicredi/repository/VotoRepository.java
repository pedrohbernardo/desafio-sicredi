package com.sicredi.projetosicredi.repository;

import com.sicredi.projetosicredi.models.Voto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VotoRepository extends CrudRepository<Voto, Long> {

    Optional<Voto> findByAssociadoIdAndPautaId(Long associadoId, Long pautaId);

    List<Voto> findAllByPautaId(Long pautaId);

}
