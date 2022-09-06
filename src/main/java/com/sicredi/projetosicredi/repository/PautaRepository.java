package com.sicredi.projetosicredi.repository;

import com.sicredi.projetosicredi.models.Pauta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PautaRepository extends CrudRepository<Pauta, Long> {

}
