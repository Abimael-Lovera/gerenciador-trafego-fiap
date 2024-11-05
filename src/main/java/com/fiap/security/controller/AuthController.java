package com.fiap.security.controller;

import com.fiap.security.dto.LoginDTO;
import com.fiap.security.dto.TokenDTO;
import com.fiap.security.service.TokenService;
import com.fiap.usuario.dto.UsuarioCreateDTO;
import com.fiap.usuario.dto.UsuarioViewDTO;
import com.fiap.usuario.model.Usuario;
import com.fiap.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginDTO loginDTO){

        UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(
                loginDTO.email(),
                loginDTO.senha());

        Authentication auth = authenticationManager.authenticate(userNamePassword);
        String token = tokenService.createToken((Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioViewDTO register(@RequestBody @Valid UsuarioCreateDTO usuarioCreateDTO){
        return usuarioService.save(usuarioCreateDTO);
    }
}
