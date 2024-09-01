package com.gestor.turnos_rotativos.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", status.value());

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        responseBody.put("errors", errors);

        return new ResponseEntity<>(responseBody, headers, status);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBussinessException(
            BusinessException ex, WebRequest request
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessRuleFieldException.class)
    public ResponseEntity<Object> handleBusinessRuleFieldException(
            BusinessRuleFieldException ex, WebRequest request
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());
        responseBody.put("field", ex.getField());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException
            ex) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());


        StringBuilder message = new StringBuilder();
        Map<String, List<String>> fieldErrors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();

            message.append(errorMessage).append("\n");

            fieldErrors.computeIfAbsent(fieldName, k -> new ArrayList<>()).add(errorMessage);
        }

        responseBody.put("message", message.toString().trim());
        responseBody.put("fields", fieldErrors);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.CONFLICT.value());

        String errorMessage = "Error de integridad de datos.";
        String errorField = "desconocido";  // Valor por defecto para el campo problemático

        Throwable rootCause = ex.getRootCause();

        if (rootCause != null) {
            String rootMessage = rootCause.getMessage();

            if (rootCause instanceof ConstraintViolationException) {

                errorMessage = "Violación de restricción - " + rootMessage;

                if (rootMessage.contains("DOCUMENTO")) {
                    errorMessage = "Ya existe un empleado con el documento ingresado.";
                    errorField = "nroDocumento";
                }

                if (rootMessage.contains("EMAIL")) {
                    errorMessage = "Ya existe un empleado con el email ingresado.";
                    errorField = "email";
                }
            } else {
                errorMessage += " " + rootMessage;

                if (rootMessage.contains("DOCUMENTO")) {
                    errorMessage = "Ya existe un empleado con el documento ingresado.";
                    errorField = "nroDocumento";
                }

                if (rootMessage.contains("EMAIL")) {
                    errorMessage = "Ya existe un empleado con el email ingresado.";
                    errorField = "email";
                }
            }
        } else {
            errorMessage += " " + ex.getMessage();
        }

        responseBody.put("message", errorMessage);
        responseBody.put("field", errorField);  // Añadir el campo problemático al cuerpo de la respuesta

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }
}
