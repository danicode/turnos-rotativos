package com.gestor.turnos_rotativos.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        logger.error("Validation error occurred: {}", ex.getMessage());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, List<String>> fieldErrors = new LinkedHashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            String field = error.getField();
            String errorMessage = error.getDefaultMessage();

            fieldErrors.computeIfAbsent(field, key -> new ArrayList<>()).add(errorMessage);
        });

        responseBody.put("fields", fieldErrors);

        String message = fieldErrors.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.joining("\n"));
        responseBody.put("message", message);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<Map<String, Object>> handleHandlerMethodValidationException(HandlerMethodValidationException ex) {

        logger.error("Handler method validation error: {}", ex.getMessage());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("status", HttpStatus.BAD_REQUEST.value());

        Map<String, List<String>> fieldErrors = ex.getAllErrors().stream()
                .filter(error -> error instanceof FieldError)
                .map(error -> (FieldError) error)
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        responseBody.put("fields", fieldErrors);
        String message = fieldErrors.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.joining("\n"));
        responseBody.put("message", message);

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBussinessException(
            BusinessException ex, WebRequest request
    ) {
        logger.error("Business exception: {}", ex.getMessage());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, ex.getStatusCode());
    }

    @ExceptionHandler(BusinessRuleFieldException.class)
    public ResponseEntity<Object> handleBusinessRuleFieldException(
            BusinessRuleFieldException ex, WebRequest request
    ) {
        logger.error("Business rule field exception: Field = {}, Message = {}", ex.getField(), ex.getMessage());

        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());
        responseBody.put("message", ex.getMessage());
        responseBody.put("field", ex.getField());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException
            ex) {
        logger.error("Constraint violation: {}", ex.getMessage());

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
        logger.error("Data integrity violation: {}", ex.getMessage());

        HttpStatusCode statusCode = HttpStatus.CONFLICT;
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("timestamp", LocalDateTime.now());

        String errorMessage = "Error de integridad de datos.";
        String errorField = "desconocido";

        Throwable rootCause = ex.getRootCause();

        if (rootCause != null) {
            String rootMessage = rootCause.getMessage();

            if (rootCause instanceof ConstraintViolationException) {

                errorMessage = "Violación de restricción - " + rootMessage;

                if (rootMessage.contains("ID_EMPLEADO") && rootMessage.contains("JORNADAS")) {
                    statusCode = HttpStatus.BAD_REQUEST;
                    errorMessage = "No es posible eliminar un empleado con jornadas asociadas.";
                    errorField = "";
                }

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

                if (rootMessage.contains("ID_EMPLEADO") && rootMessage.contains("JORNADAS")) {
                    statusCode = HttpStatus.BAD_REQUEST;
                    errorMessage = "No es posible eliminar un empleado con jornadas asociadas.";
                    errorField = "";
                }

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

        responseBody.put("status", statusCode.value());
        responseBody.put("message", errorMessage);
        if (!errorField.isEmpty()) {
            responseBody.put("field", errorField);
        }
        return new ResponseEntity<>(responseBody, statusCode);
    }
}
