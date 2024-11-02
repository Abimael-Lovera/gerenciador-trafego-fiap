package com.fiap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(AppGerenciadorTrafegoException.class)
    public ProblemDetail handleAppGerenciadorTrafegoException(AppGerenciadorTrafegoException ex) {
        System.out.println("Entrei na exception do Generica da app");
        return ex.toProblemDetail();
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail tratarErro500(Exception ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Erro no servidor");
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> detalhes = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            detalhes.put(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Erro na validação existe um ou mais campos inválidos");
        pd.setProperty("detalhes", detalhes);
        return pd;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pd.setTitle("Erro na requisição");
        pd.setDetail(ex.getMessage());
        return pd;
    }
}
