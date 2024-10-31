package com.fiap.acidente.controller;

import com.fiap.acidente.dto.AcidenteCreateDTO;
import com.fiap.acidente.dto.AcidenteUpdateDTO;
import com.fiap.acidente.dto.AcidenteViewDTO;
import com.fiap.acidente.service.AcidenteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/acidentes")
public class AcidenteController {
    private final AcidenteService acidenteService;

    public AcidenteController(AcidenteService acidenteService) {
        this.acidenteService = acidenteService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AcidenteViewDTO> save(@Valid @RequestBody AcidenteCreateDTO acidenteCreateDTO) {
        return ResponseEntity.ok(acidenteService.save(acidenteCreateDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AcidenteViewDTO>> findAll(Pageable paginacao) {
        return ResponseEntity.ok(acidenteService.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcidenteViewDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(acidenteService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<AcidenteViewDTO> updateById (@PathVariable Integer id, @Valid @RequestBody AcidenteUpdateDTO acidenteUpdateDTO) {
        return ResponseEntity.ok(acidenteService.updateById(id, acidenteUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<AcidenteViewDTO> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok(acidenteService.deleteById(id));
    }

}
