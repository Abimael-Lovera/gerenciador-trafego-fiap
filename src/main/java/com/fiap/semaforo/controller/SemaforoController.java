package com.fiap.semaforo.controller;

import com.fiap.semaforo.dto.SemaforoCreateDTO;
import com.fiap.semaforo.dto.SemaforoUpdateDTO;
import com.fiap.semaforo.dto.SemaforoViewDTO;
import com.fiap.semaforo.service.SemaforoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/semaforos")
public class SemaforoController {

    private final SemaforoService semaforoService;

    public SemaforoController(SemaforoService semaforoService) {
        this.semaforoService = semaforoService;
    }

    @PostMapping
    public ResponseEntity<SemaforoViewDTO> criarSemaforo(@Valid @RequestBody SemaforoCreateDTO semaforoCreateDTO) {
        return ResponseEntity.ok(semaforoService.save(semaforoCreateDTO));
    }

    @GetMapping
    public ResponseEntity<Page<SemaforoViewDTO>> findAll(Pageable paginacao) {
        return ResponseEntity.ok(semaforoService.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SemaforoViewDTO> buscarSemaforoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(semaforoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SemaforoViewDTO> atualizarSemaforo(
            @PathVariable Integer id,
            @Valid @RequestBody SemaforoUpdateDTO semaforoUpdateDTO) {
        ;
        return ResponseEntity.ok(semaforoService.updateById(id, semaforoUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SemaforoViewDTO> deletarSemaforo(@PathVariable Integer id) {

        return ResponseEntity.ok(semaforoService.deleteById(id));
    }
}
