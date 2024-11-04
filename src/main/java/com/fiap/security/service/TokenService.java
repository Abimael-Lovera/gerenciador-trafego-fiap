package com.fiap.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.fiap.usuario.model.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${my.secret.key}")
    private String secretKey;

    public String createToken(Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            String token = JWT
                    .create()
                    .withIssuer("Gerenciador de Trafego")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(createExpirationDate())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException erro){
            throw  new RuntimeException("Não foi possivel gerar o token!");
        }
    }

    public String validationToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("Gerenciador de Trafego")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTCreationException erro) {
            return "";
        }
    }

    public Instant createExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
