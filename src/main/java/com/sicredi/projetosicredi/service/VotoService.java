package com.sicredi.projetosicredi.service;

import com.sicredi.projetosicredi.models.Associado;
import com.sicredi.projetosicredi.models.Pauta;
import com.sicredi.projetosicredi.models.Voto;
import com.sicredi.projetosicredi.models.dto.VotoDTO;
import com.sicredi.projetosicredi.repository.AssociadoRepository;
import com.sicredi.projetosicredi.repository.PautaRepository;
import com.sicredi.projetosicredi.repository.VotoRepository;
import com.sicredi.projetosicredi.util.Constants;
import com.sicredi.projetosicredi.util.exceptions.AssociadoNaoEncontradoException;
import com.sicredi.projetosicredi.util.exceptions.PautaNaoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VotoService {

    @Autowired
    private VotoRepository repository;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    public String votar(VotoDTO voto) {
        Optional<Pauta> pautaEncontrada = pautaRepository.findById(voto.getIdPauta());
        if (pautaEncontrada.isPresent()) {
            if (pautaEncontrada.get().getStatusPauta()) {
                Optional<Associado> associadoEncontrado = associadoRepository.findByCpf(voto.getCpfAssociado());
                if (associadoEncontrado.isPresent()) {
                    Optional<Voto> votoEncontrado = repository.findByAssociadoIdAndPautaId(
                            associadoEncontrado.get().getId(),
                            pautaEncontrada.get().getId());
                    if (votoEncontrado.isEmpty()) {
                        Voto novoVoto = new Voto();
                        novoVoto.setAssociado(associadoEncontrado.get());
                        novoVoto.setPauta(pautaEncontrada.get());
                        novoVoto.setVotou(voto.getVoto());
                        repository.save(novoVoto);
                        return Constants.VOTO_EFETUADO;
                    }
                    return Constants.VOTO_REGISTRADO;
                }
                throw new AssociadoNaoEncontradoException(Constants.ASSOCIADO_INEXISTENTE);
            }
            return Constants.PAUTA_FINALIZADA;
        }
        throw new PautaNaoEncontradaException(Constants.PAUTA_INEXISTENTE);
    }

}
