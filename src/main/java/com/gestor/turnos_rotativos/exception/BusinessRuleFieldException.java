package com.gestor.turnos_rotativos.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessRuleFieldException extends RuntimeException {

    private String field;

    public BusinessRuleFieldException(String message, String field) {
        super(message);
        this.field = field;
    }
}
