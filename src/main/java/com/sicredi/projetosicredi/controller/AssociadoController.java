package com.sicredi.projetosicredi.controller;

import com.sicredi.projetosicredi.models.Associado;
import com.sicredi.projetosicredi.models.dto.AssociadoDTO;
import com.sicredi.projetosicredi.service.AssociadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AssociadoController {

    @Autowired
    private AssociadoService service;

    @RequestMapping(value = "/associado", method = RequestMethod.POST)
    public ResponseEntity<Associado> create(@RequestBody AssociadoDTO associadoDTO) {
        return service.create(associadoDTO);
    }

    @RequestMapping(value = "/associado/{id}", method = RequestMethod.GET)
    public ResponseEntity<Associado> getAssociado(@PathVariable Long id) {
        return ResponseEntity.ok(service.getAssociado(id));
    }

    @RequestMapping(value = "/associado/{id}", method = RequestMethod.DELETE)
    public String deleteAssociado(@PathVariable Long id){ return service.deleteAssociado(id);}
}
