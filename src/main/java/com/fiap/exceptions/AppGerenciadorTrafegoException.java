package com.fiap.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AppGerenciadorTrafegoException extends RuntimeException{

    public ProblemDetail toProblemDetail() {
        var pd = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pd.setTitle("Gerenciador de trafego FIAP - Internal Server Error");
        return pd;
    }

}
