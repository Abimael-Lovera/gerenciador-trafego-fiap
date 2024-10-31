package com.fiap.semaforo.exception;

import com.fiap.exceptions.AppGerenciadorTrafegoException;
import org.springframework.http.ProblemDetail;

public class SemaforoNaoEncontradoException extends AppGerenciadorTrafegoException {
    private final Integer idSemaforo;
    public SemaforoNaoEncontradoException(Integer idSemaforo) {
        this.idSemaforo = idSemaforo;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(404);
        pd.setTitle("Não foi possível encontrar o semaforo com id: " + idSemaforo);
        return pd;
    }
}
