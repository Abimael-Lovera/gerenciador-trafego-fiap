package com.fiap.rota.controller;

import com.fiap.rota.dto.RotaCreateDTO;
import com.fiap.rota.dto.RotaUpdateDTO;
import com.fiap.rota.dto.RotaViewDTO;
import com.fiap.rota.service.RotaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rotas")
public class RotaController {
    private final RotaService rotaService;

    public RotaController(RotaService rotaService) {
        this.rotaService = rotaService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<RotaViewDTO> salvar(@RequestBody @Valid RotaCreateDTO rotaCreateDTO) {
        return ResponseEntity.ok(rotaService.save(rotaCreateDTO));
    }

    @GetMapping
    public ResponseEntity<Page<RotaViewDTO>> listarTodos(@PageableDefault(size = 5, page = 0) Pageable paginacao) {
        return ResponseEntity.ok(rotaService.findAll(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RotaViewDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(rotaService.findById(id));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RotaViewDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid RotaUpdateDTO rotaUpdateDTO) {
        return ResponseEntity.ok(rotaService.updateById(id, rotaUpdateDTO));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<RotaViewDTO> excluir(@PathVariable Integer id) {
        return ResponseEntity.ok(rotaService.deleteById(id));
    }

}
