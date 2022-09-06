package com.sicredi.projetosicredi.service;

import com.sicredi.projetosicredi.models.Associado;
import com.sicredi.projetosicredi.models.dto.AssociadoDTO;
import com.sicredi.projetosicredi.repository.AssociadoRepository;
import com.sicredi.projetosicredi.util.Constants;
import com.sicredi.projetosicredi.util.CpfValidateUtil;
import com.sicredi.projetosicredi.util.exceptions.AssociadoExistenteException;
import com.sicredi.projetosicredi.util.exceptions.AssociadoNaoEncontradoException;
import com.sicredi.projetosicredi.util.exceptions.CpfInvalidoException;
import com.sicredi.projetosicredi.util.mapper.AssociadoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociadoService {

    @Autowired
    private AssociadoRepository repository;

    private AssociadoMapper mapper;

    public ResponseEntity<Associado> create(AssociadoDTO associado) {
        Optional<Associado> associadoEncontrado = repository.findByCpf(associado.getCpf());
        if (associadoEncontrado.isPresent()) {
            throw new AssociadoExistenteException(Constants.ASSOCIADO_EXISTENTE);
        }
        if (CpfValidateUtil.isCPF(associado.getCpf())) {
            Associado associadoCriado = repository.save(mapper.toEntity(associado));
            return ResponseEntity.ok(associadoCriado);
        }
        throw new CpfInvalidoException(Constants.CPF_INVALIDO);
    }

    public Associado getAssociado(Long id) {
        Optional<Associado> associadoEncontrado = repository.findById(id);
        if (associadoEncontrado.isPresent()) {
            return associadoEncontrado.get();
        }
        throw new AssociadoNaoEncontradoException(Constants.ASSOCIADO_INEXISTENTE);
    }

    public String deleteAssociado(Long id) {
        Optional<Associado> associadoEncontrado = repository.findById(id);
        if (associadoEncontrado.isPresent()) {
            repository.delete(associadoEncontrado.get());
            return Constants.ASSOCIADO_DELETADO;
        }
        throw new AssociadoNaoEncontradoException(Constants.ASSOCIADO_INEXISTENTE);
    }

}
