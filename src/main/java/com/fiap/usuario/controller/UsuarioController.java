package com.fiap.usuario.controller;


import com.fiap.usuario.dto.UsuarioCreateDTO;
import com.fiap.usuario.dto.UsuarioUpdateDTO;
import com.fiap.usuario.dto.UsuarioViewDTO;
import com.fiap.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioViewDTO save(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return usuarioService.save(usuarioCreateDTO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<UsuarioViewDTO> findAll(@PageableDefault(size = 2, page = 0) Pageable paginacao){
        return usuarioService.findAll(paginacao);
    }

    @GetMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioViewDTO> findById(@PathVariable Long usuarioId){
        return ResponseEntity.ok(usuarioService.findById(usuarioId));
    }

    @DeleteMapping("/{usuarioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long usuarioId){
        usuarioService.deleteById(usuarioId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<UsuarioViewDTO> update(@RequestBody @Valid UsuarioUpdateDTO usuarioUpdateDTO){
        return  ResponseEntity.ok(usuarioService.update(usuarioUpdateDTO));
    }

}