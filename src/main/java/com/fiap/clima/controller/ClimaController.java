package com.fiap.clima.controller;

import com.fiap.clima.dto.ClimaCreateDTO;
import com.fiap.clima.dto.ClimaUpdateDTO;
import com.fiap.clima.dto.ClimaViewDTO;
import com.fiap.clima.service.ClimaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/clima")
public class ClimaController {
    private static final Logger log = LoggerFactory.getLogger(ClimaController.class);
    private final ClimaService climaService;

    public ClimaController(ClimaService climaService) {
        this.climaService = climaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ClimaViewDTO> create(@RequestBody @Validated ClimaCreateDTO clima) {
        log.info("Criando clima: {}", clima);
        return ResponseEntity.status(HttpStatus.CREATED).body(climaService.save(clima));
    }

    @GetMapping
    public ResponseEntity<Page<ClimaViewDTO>> findAll(@PageableDefault(size = 10, sort = {"idClima"}) Pageable paginacao) {
        log.info("Buscando todos os climas");
        return ResponseEntity.status(HttpStatus.OK).body(climaService.findAll(paginacao));
    }

    @GetMapping("/{idClima}")
    public ResponseEntity<ClimaViewDTO> findById(@PathVariable Long idClima) {
        log.info("Buscando clima com id: {}", idClima);
        return ResponseEntity.ok(climaService.findById(idClima));
    }

    @PutMapping("/{idClima}")
    @Transactional
    public ResponseEntity<ClimaViewDTO> updateById(@RequestBody @Validated ClimaUpdateDTO climaUpdateDTO, @PathVariable Long idClima) {
        log.info("Atualizando clima: {}", climaUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(climaService.updateById(climaUpdateDTO, idClima));
    }

    @DeleteMapping("/{idClima}")
    @Transactional
    public ResponseEntity<ClimaViewDTO> deleteById(@PathVariable Long idClima) {
        log.info("Deletando clima com id: {}", idClima);
        return ResponseEntity.status(HttpStatus.OK).body(climaService.deleteById(idClima));
    }
}

