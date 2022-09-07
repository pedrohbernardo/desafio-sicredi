package com.sicredi.projetosicredi.service;

import com.sicredi.projetosicredi.models.Pauta;
import com.sicredi.projetosicredi.models.dto.PautaDTO;
import com.sicredi.projetosicredi.models.dto.StatusPautaDTO;
import com.sicredi.projetosicredi.repository.PautaRepository;
import com.sicredi.projetosicredi.repository.VotoRepository;
import com.sicredi.projetosicredi.util.Constants;
import com.sicredi.projetosicredi.util.exceptions.PautaNaoEncontradaException;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PautaServiceTests {

    public static final Long ID = 1L;

    @Mock
    private PautaRepository repository;

    @Mock
    private VotoRepository votoRepository;

    @InjectMocks
    private PautaService service;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public Pauta novaPauta(Boolean status, Boolean tempoFinal) {
        Pauta novaPauta = new Pauta();
        novaPauta.setId(ID);
        novaPauta.setNomePauta("Pauta Teste");
        novaPauta.setStatusPauta(status);
        novaPauta.setTempoFinalPauta(tempoFinal ? LocalDateTime.now() : null);
        novaPauta.setTempoDuracaoPauta(1L);
        return novaPauta;
    }

    @Test
    public void getPauta() {

        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.FALSE, Boolean.FALSE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);

        Pauta pautaEncontrada = service.getPauta(ID);

        Assertions.assertEquals(pautaEncontrada.getId(), pauta.get().getId());
        Assertions.assertEquals(pautaEncontrada.getTempoDuracaoPauta(), pauta.get().getTempoDuracaoPauta());
        Assertions.assertEquals(pautaEncontrada.getNomePauta(), pauta.get().getNomePauta());
    }

    @Test
    public void getPautaThrowException() {

        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.getPauta(ID));
    }

    @Test
    public void deletePauta() {

        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.FALSE, Boolean.FALSE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);

        String pautaDeletada = service.deletePauta(ID);

        assertEquals(pautaDeletada, Constants.PAUTA_DELETADA);
    }

    @Test
    public void deletePautaThrowException() {
        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.deletePauta(ID));
    }

    @Test
    public void createPauta() {
        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setNomePauta("Pauta Teste");
        when(repository.save(any(Pauta.class))).thenReturn(novaPauta(Boolean.FALSE, Boolean.FALSE));

        ResponseEntity<Pauta> pautaCriada = service.create(pautaDTO);
        assertEquals(pautaCriada.getBody().getNomePauta(), pautaDTO.getNomePauta());
        assertEquals(pautaCriada.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void iniciarPauta() {
        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.FALSE, Boolean.FALSE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);

        String status = service.iniciarPauta(ID);

        assertEquals(status, Constants.PAUTA_INICIADA);

    }

    @Test
    public void iniciarPautaJaFinalizada() {
        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.FALSE, Boolean.TRUE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);

        String status = service.iniciarPauta(ID);

        assertEquals(status, Constants.PAUTA_FINALIZADA);
    }

    @Test
    public void iniciarPautaJaIniciada() {
        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.TRUE, Boolean.TRUE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);

        String status = service.iniciarPauta(ID);

        assertEquals(status, Constants.PAUTA_EM_ANDAMENTO);
    }

    @Test
    public void iniciarPautaNaoExiste() {
        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.iniciarPauta(ID));
    }

    @Test
    public void getResultadoPauta() {
        Optional<Pauta> pauta = Optional.of(novaPauta(Boolean.FALSE, Boolean.TRUE));
        when(repository.findById(any(Long.TYPE))).thenReturn(pauta);
        when(votoRepository.findAllByPautaId(ID)).thenReturn(anyList());

        StatusPautaDTO resultadoPauta = service.getResultadoPauta(ID);

        assertNotNull(resultadoPauta);
        assertEquals(resultadoPauta.getNomePauta(), pauta.get().getNomePauta());

    }

    @Test
    public void getResultadoPautaThrowException() {
        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(PautaNaoEncontradaException.class, () -> service.getResultadoPauta(ID));
    }
}
