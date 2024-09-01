package com.gestor.turnos_rotativos.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class BusinessException extends RuntimeException {

    private HttpStatusCode statusCode;

    public BusinessException(String message) {
        super(message);
        this.statusCode = HttpStatus.CONFLICT;
    }

    public BusinessException(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
