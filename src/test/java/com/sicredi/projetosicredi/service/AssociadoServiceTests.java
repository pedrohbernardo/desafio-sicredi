package com.sicredi.projetosicredi.service;

import com.sicredi.projetosicredi.models.Associado;
import com.sicredi.projetosicredi.models.dto.AssociadoDTO;
import com.sicredi.projetosicredi.repository.AssociadoRepository;
import com.sicredi.projetosicredi.util.Constants;
import com.sicredi.projetosicredi.util.exceptions.AssociadoExistenteException;
import com.sicredi.projetosicredi.util.exceptions.AssociadoNaoEncontradoException;
import com.sicredi.projetosicredi.util.exceptions.CpfInvalidoException;
import com.sicredi.projetosicredi.util.mapper.AssociadoMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AssociadoServiceTests {

    public static final String CPF = "95930606005";
    public static final String INVALID_CPF = "95930606000";

    @Mock
    private AssociadoRepository repository;

    @Mock
    private AssociadoMapper mapper;

    @InjectMocks
    private AssociadoService service;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    public Associado novoAssociado() {
        return new Associado(1l, CPF);
    }

    public AssociadoDTO associadoDTO(String cpf) {
        AssociadoDTO associadoDTO = new AssociadoDTO();
        associadoDTO.setCpf(cpf);
        return associadoDTO;
    }

    @Test
    public void getAssociado() {

        Optional<Associado> associado = Optional.of(novoAssociado());
        when(repository.findById(any(Long.TYPE))).thenReturn(associado);

        Associado associadoEncontrado = service.getAssociado(1L);

        Assertions.assertEquals(associadoEncontrado.getId(), associado.get().getId());
        Assertions.assertEquals(associadoEncontrado.getCpf(), associado.get().getCpf());
    }

    @Test
    public void getAssociadoThrowException() {
        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(AssociadoNaoEncontradoException.class, () -> service.getAssociado(1L));
    }

    @Test
    public void deleteAssociado() {

        Optional<Associado> associado = Optional.of(novoAssociado());
        when(repository.findById(any(Long.TYPE))).thenReturn(associado);

        String associadoDeletado = service.deleteAssociado(1L);

        assertEquals(associadoDeletado, Constants.ASSOCIADO_DELETADO);
    }

    @Test
    public void deleteAssociadoThrowException() {
        when(repository.findById(any(Long.TYPE))).thenReturn(Optional.empty());

        assertThrows(AssociadoNaoEncontradoException.class, () -> service.deleteAssociado(1L));
    }

    @Test
    public void createAssociado() {
        when(repository.findByCpf(anyString())).thenReturn(Optional.empty());
        when(repository.save(any(Associado.class))).thenReturn(novoAssociado());

        ResponseEntity<Associado> associadoCriado = service.create(associadoDTO(CPF));

        assertEquals(associadoCriado.getBody().getCpf(), associadoDTO(CPF).getCpf());
        assertNotNull(associadoCriado.getBody().getId());
    }

    @Test
    public void createAssociadoThrowException() {

        Optional<Associado> associado = Optional.of(novoAssociado());
        when(repository.findByCpf(anyString())).thenReturn(associado);

        assertThrows(AssociadoExistenteException.class, () -> service.create(associadoDTO(CPF)));
    }

    @Test
    public void createAssociadoThrowCPFException() {

        when(repository.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThrows(CpfInvalidoException.class, () -> service.create(associadoDTO(INVALID_CPF)));
    }


}
