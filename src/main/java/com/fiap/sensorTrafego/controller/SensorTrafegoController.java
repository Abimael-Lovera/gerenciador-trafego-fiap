package com.fiap.sensorTrafego.controller;

import com.fiap.sensorTrafego.dto.SensorTrafegoCreateDTO;
import com.fiap.sensorTrafego.dto.SensorTrafegoUpdateDTO;
import com.fiap.sensorTrafego.dto.SensorTrafegoViewDTO;
import com.fiap.sensorTrafego.service.SensorTrafegoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensorTrafego")
public class SensorTrafegoController {

    private final SensorTrafegoService sensorTrafegoService;

    public SensorTrafegoController(SensorTrafegoService sensorTrafegoService) {
        this.sensorTrafegoService = sensorTrafegoService;
    }

    @PostMapping
    public ResponseEntity<SensorTrafegoViewDTO> save(@Valid @RequestBody SensorTrafegoCreateDTO sensorTrafegoCreateDTO) {
        return ResponseEntity.ok(sensorTrafegoService.save(sensorTrafegoCreateDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorTrafegoViewDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(sensorTrafegoService.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SensorTrafegoViewDTO>> findAll(Pageable paginacao) {
        return ResponseEntity.ok(sensorTrafegoService.findAll(paginacao));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SensorTrafegoViewDTO> updateById(@Valid @RequestBody SensorTrafegoUpdateDTO sensorTrafegoUpdateDTO, @PathVariable Integer id) {
        return ResponseEntity.ok(sensorTrafegoService.updateById(id, sensorTrafegoUpdateDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SensorTrafegoViewDTO> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(sensorTrafegoService.deleteById(id));
    }
}
