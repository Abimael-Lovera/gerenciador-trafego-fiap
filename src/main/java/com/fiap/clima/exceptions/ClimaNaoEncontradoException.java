package com.fiap.clima.exceptions;

import com.fiap.exceptions.AppGerenciadorTrafegoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class ClimaNaoEncontradoException extends AppGerenciadorTrafegoException {

    private final Long idClima;

    public ClimaNaoEncontradoException(Long idClima) {
        this.idClima = idClima;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Clima não encontrado");
        pd.setDetail("Não foi possível encontrar o clima com id: " + this.idClima);
        return pd;
    }
}
