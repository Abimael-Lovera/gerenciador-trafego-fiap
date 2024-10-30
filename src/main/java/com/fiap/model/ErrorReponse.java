package com.fiap.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorReponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private Map<String, String> detalhes;

    public ErrorReponse(LocalDateTime timestamp, int status, String message, Map<String, String> detalhes) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
        this.detalhes = detalhes;
    }

    public ErrorReponse(LocalDateTime timestamp, int status, String message) {
        this.timestamp = timestamp;
        this.status = status;
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(Map<String, String> detalhes) {
        this.detalhes = detalhes;
    }
}
