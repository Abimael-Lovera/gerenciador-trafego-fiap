package com.fiap.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerController {
    private static final Logger log = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(AppGerenciadorTrafegoException.class)
    public ProblemDetail handleAppGerenciadorTrafegoException(AppGerenciadorTrafegoException ex) {
        log.error("Entrei na exception do Generica da app");
        return ex.toProblemDetail();
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ProblemDetail handleNoResourceFoundException(NoResourceFoundException ex) {
        log.error("NoResourceFoundException: ", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Não foi possível encontrar o endpoint");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", LocalDateTime.now());
        return pd;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex) {
        log.error("Exception: ", ex);
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Erro no servidor");
        pd.setDetail(ex.getMessage());
        pd.setProperty("timestamp", LocalDateTime.now());
        return pd;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.CONFLICT);
        pd.setTitle("Usuário já cadastrado!");
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail tratarErroBadCredentials(BadCredentialsException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Credenciais inválidas");
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(AuthenticationException.class)
    public ProblemDetail tratarErroAuthentication(AuthenticationException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        pd.setTitle("Falha na autenticação");
        pd.setDetail(ex.getMessage());
        return pd;
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail tratarErroAcessoNegado(AccessDeniedException ex) {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.FORBIDDEN);
        pd.setTitle("Acesso negado");
        pd.setDetail(ex.getMessage());
        return pd;
    }

}
