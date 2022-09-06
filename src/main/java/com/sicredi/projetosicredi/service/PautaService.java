package com.sicredi.projetosicredi.service;

import com.sicredi.projetosicredi.models.Pauta;
import com.sicredi.projetosicredi.models.Voto;
import com.sicredi.projetosicredi.models.dto.PautaDTO;
import com.sicredi.projetosicredi.models.dto.StatusPautaDTO;
import com.sicredi.projetosicredi.repository.PautaRepository;
import com.sicredi.projetosicredi.repository.VotoRepository;
import com.sicredi.projetosicredi.util.Constants;
import com.sicredi.projetosicredi.util.exceptions.PautaNaoEncontradaException;
import com.sicredi.projetosicredi.util.mapper.PautaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PautaService {

    @Autowired
    private PautaRepository repository;

    @Autowired
    private VotoRepository votoRepository;

    private PautaMapper mapper;

    public ResponseEntity<Pauta> create(PautaDTO pautaDTO) {
        Pauta pautaCriada = repository.save(mapper.toEntity(pautaDTO));
        return ResponseEntity.ok(pautaCriada);
    }

    public String iniciarPauta(Long id) {
        Optional<Pauta> pautaEncontrada = repository.findById(id);
        if (pautaEncontrada.isPresent()) {
            Pauta iniciarPauta = pautaEncontrada.get();
            LocalDateTime horaAtual = LocalDateTime.now();
            if (iniciarPauta.getStatusPauta() != Boolean.TRUE) {
                if (iniciarPauta.getTempoFinalPauta() == null) {
                    iniciarPauta.setStatusPauta(Boolean.TRUE);
                    iniciarPauta.setTempoFinalPauta(horaAtual.plusMinutes(iniciarPauta.getTempoDuracaoPauta()));
                    repository.save(iniciarPauta);
                    return Constants.PAUTA_INICIADA;
                }
                return Constants.PAUTA_FINALIZADA;
            }
            return encerrarPauta(iniciarPauta);
        }
        throw new PautaNaoEncontradaException(Constants.PAUTA_INEXISTENTE);
    }

    public String encerrarPauta(Pauta pauta) {
        if (pauta.getStatusPauta() == Boolean.TRUE) {
            if (pauta.getTempoFinalPauta().isBefore(LocalDateTime.now())) {
                pauta.setStatusPauta(Boolean.FALSE);
                repository.save(pauta);
            }
            return Constants.PAUTA_EM_ANDAMENTO;
        }
        return Constants.PAUTA_FINALIZADA;
    }

    public Pauta getPauta(Long id) {
        Optional<Pauta> pautaEncontrada = repository.findById(id);
        if (pautaEncontrada.isPresent()) {
            return pautaEncontrada.get();
        }
        throw new PautaNaoEncontradaException(Constants.PAUTA_INEXISTENTE);
    }

    public String deletePauta(Long id) {
        Optional<Pauta> pautaEncontrada = repository.findById(id);
        if (pautaEncontrada.isPresent()) {
            repository.delete(pautaEncontrada.get());
            return Constants.PAUTA_DELETADA;
        }
        throw new PautaNaoEncontradaException(Constants.PAUTA_INEXISTENTE);
    }

    public StatusPautaDTO getResultadoPauta(Long id) {
        Optional<Pauta> pautaEncontrada = repository.findById(id);
        if (pautaEncontrada.isPresent()) {
            List<Voto> votos = votoRepository.findAllByPautaId(id);
            StatusPautaDTO resultadoPauta = new StatusPautaDTO();
            resultadoPauta.setNomePauta(pautaEncontrada.get().getNomePauta());
            resultadoPauta.setQuantidadeVotosNao(votos.stream().filter(
                    voto -> voto.getVotou().equals(Boolean.FALSE)).count());
            resultadoPauta.setQuantidadeVotosSim(votos.stream().filter(
                    voto -> voto.getVotou().equals(Boolean.TRUE)).count());
            return resultadoPauta;
        }
        throw new PautaNaoEncontradaException(Constants.PAUTA_INEXISTENTE);
    }
}
