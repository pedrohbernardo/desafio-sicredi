package com.sicredi.projetosicredi.controller;

import com.sicredi.projetosicredi.models.dto.VotoDTO;
import com.sicredi.projetosicredi.service.VotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class VotoController {

    @Autowired
    private VotoService service;

    @RequestMapping(value = "/votar", method = RequestMethod.POST)
    public ResponseEntity<String> votar(@RequestBody VotoDTO voto) {
        return ResponseEntity.ok(service.votar(voto));
    }
}
