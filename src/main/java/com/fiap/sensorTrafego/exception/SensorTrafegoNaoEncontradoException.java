package com.fiap.sensorTrafego.exception;

import com.fiap.exceptions.AppGerenciadorTrafegoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class SensorTrafegoNaoEncontradoException extends AppGerenciadorTrafegoException {
    private Integer id;

    public SensorTrafegoNaoEncontradoException(Integer id) {
        this.id = id;
    }

    @Override
    public ProblemDetail toProblemDetail() {
        ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        pd.setTitle("Não foi possível encontrar o sensorTrafego com id: " + id);
        return pd;
    }
}
