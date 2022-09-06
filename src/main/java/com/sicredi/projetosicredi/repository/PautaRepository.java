package com.sicredi.projetosicredi.repository;

import com.sicredi.projetosicredi.models.Pauta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {

    List<Pauta> findAllByStatusPautaIsTrue();

}
