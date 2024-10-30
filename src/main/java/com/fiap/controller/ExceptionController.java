package com.fiap.controller;

import com.fiap.exceptions.ClimaNaoEncontradaException;
import com.fiap.model.ErrorReponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorReponse> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> detalhes = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            detalhes.put(error.getField(), error.getDefaultMessage());
        }
        ErrorReponse errorResponse = new ErrorReponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Erro na validacao dos dados",
                detalhes
        );
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(ClimaNaoEncontradaException.class)
    public ResponseEntity<ErrorReponse> handleClimaNaoEncontradoException(ClimaNaoEncontradaException ex) {
        ErrorReponse errorResponse = new ErrorReponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorReponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ErrorReponse errorResponse = new ErrorReponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
