package com.sicredi.projetosicredi.controller;

import com.sicredi.projetosicredi.models.Pauta;
import com.sicredi.projetosicredi.models.dto.PautaDTO;
import com.sicredi.projetosicredi.models.dto.StatusPautaDTO;
import com.sicredi.projetosicredi.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PautaController {

    @Autowired
    private PautaService service;

    @RequestMapping(value = "/pauta", method = RequestMethod.POST)
    public ResponseEntity<Pauta> create(@RequestBody PautaDTO pautaDTO){
        return service.create(pautaDTO);
    }

    @RequestMapping(value = "/pauta/iniciar/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> iniciarPauta(@PathVariable Long id){
        return ResponseEntity.ok(service.iniciarPauta(id));
    }

    @RequestMapping(value = "/pauta/{id}", method = RequestMethod.GET)
    public ResponseEntity<Pauta> getPauta(@PathVariable Long id) {
        return ResponseEntity.ok(service.getPauta(id));
    }

    @RequestMapping(value = "/pauta/{id}", method = RequestMethod.DELETE)
    public String deletePauta(@PathVariable Long id) {
        return service.deletePauta(id);
    }

    @RequestMapping(value = "/pauta/resultado/{id}", method = RequestMethod.GET)
    public StatusPautaDTO getResultadoPauta(@PathVariable Long id) { return service.getResultadoPauta(id);}
}
