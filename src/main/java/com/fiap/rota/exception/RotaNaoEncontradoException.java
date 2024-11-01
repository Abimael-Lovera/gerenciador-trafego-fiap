package com.fiap.rota.exception;

import com.fiap.exceptions.AppGerenciadorTrafegoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class RotaNaoEncontradoException extends AppGerenciadorTrafegoException {
    private Integer id;

    public RotaNaoEncontradoException(Integer id) {
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Não foi possivel encontrar a rota com id : " + id);
        return pd;
    }
}
