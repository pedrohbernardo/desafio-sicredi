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
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VotoServiceTests {

    public static final Long ID = 1L;
    public static final String CPF = "95930606005";
    public static final String VOTO_SIM = "Sim";

    @Mock
    private VotoRepository repository;

    @Mock
    private AssociadoRepository associadoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private VotoService service;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public VotoDTO voto() {
        VotoDTO voto = new VotoDTO();
        voto.setIdPauta(ID);
        voto.setCpfAssociado(CPF);
        voto.setVoto(VOTO_SIM);
        return voto;
    }

    public Optional<Pauta> novaPauta(Boolean status) {
        Pauta novaPauta = new Pauta();
        novaPauta.setId(ID);
        novaPauta.setNomePauta("Pauta Teste");
        novaPauta.setStatusPauta(status);
        novaPauta.setTempoFinalPauta(LocalDateTime.now());
        novaPauta.setTempoDuracaoPauta(1L);
        return Optional.of(novaPauta);
    }

    public Optional<Associado> novoAssociado() {
        return Optional.of(new Associado(1l, CPF));
    }

    @Test
    public void votar() {

        when(pautaRepository.findById(any(Long.TYPE))).thenReturn(novaPauta(Boolean.TRUE));
        when(associadoRepository.findByCpf(CPF)).thenReturn(novoAssociado());
        when(repository.findByAssociadoIdAndPautaId(anyLong(), anyLong())).thenReturn(Optional.empty());

        String votar = service.votar(voto());
        assertEquals(votar, Constants.VOTO_EFETUADO);
    }

    @Test
    public void votarJaEfetuado() {
        Voto voto = new Voto();

        when(pautaRepository.findById(any(Long.TYPE))).thenReturn(novaPauta(Boolean.TRUE));
        when(associadoRepository.findByCpf(CPF)).thenReturn(novoAssociado());
        when(repository.findByAssociadoIdAndPautaId(anyLong(), anyLong())).thenReturn(Optional.of(voto));

        String votar = service.votar(voto());
        assertEquals(votar, Constants.VOTO_REGISTRADO);
    }

    @Test
    public void votarThrowAssociadoNaoEncontradoException() {

        when(pautaRepository.findById(any(Long.TYPE))).thenReturn(novaPauta(Boolean.TRUE));
        when(associadoRepository.findByCpf(CPF)).thenReturn(Optional.empty());

        assertThrows(AssociadoNaoEncontradoException.class, () -> service.votar(voto()));
    }

    @Test
    public void votarPautaFinalizada() {

        when(pautaRepository.findById(any(Long.TYPE))).thenReturn(novaPauta(Boolean.FALSE));

        String pautaFinalizada = service.votar(voto());

        assertEquals(pautaFinalizada, Constants.PAUTA_FINALIZADA);
    }

    @Test
    public void votarThrowPautaNaoEncontradaException() {

        when(pautaRepository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.votar(voto()));
    }
}
