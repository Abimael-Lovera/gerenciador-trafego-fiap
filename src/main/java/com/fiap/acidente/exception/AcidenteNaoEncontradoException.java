package com.fiap.acidente.exception;

import com.fiap.exceptions.AppGerenciadorTrafegoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AcidenteNaoEncontradoException extends AppGerenciadorTrafegoException {

    private final Integer id;
    public AcidenteNaoEncontradoException(Integer id) {
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Não foi possível encontrar o acidente com id: " + id);
        return pd;
    }
}
