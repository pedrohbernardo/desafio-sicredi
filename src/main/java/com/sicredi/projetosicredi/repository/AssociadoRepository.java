package com.sicredi.projetosicredi.repository;

import com.sicredi.projetosicredi.models.Associado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociadoRepository extends CrudRepository<Associado, Long> {

    Optional<Associado> findByCpf(String cpf);
}
