package com.roomhotel.roomhotel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.MethodNotAllowedException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// intercepta excepciones de TODOS los controllers y devuelve json limpio
@RestControllerAdvice
public class GlobalExceptionHandler {

    // habitación no encontrada HTTP 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // nombre duplicado HTTP 409
    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicate(DuplicateNameException ex) {
        return buildError(HttpStatus.CONFLICT, ex.getMessage());
    }

    // datos inválidos HTTP 400
    // cuando los datos del frontend no pasan las validaciones
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        // adjunto todos los errores de validación en un Map
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            fieldErrors.put(field, message);
            // { "name": "el nombre es obligatorio", "price": "el precio es obligatorio" }
        });

        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", 400);
        response.put("error", "Datos inválidos");
        response.put("fieldErrors", fieldErrors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // cualquier otro error inesperado HTTP 500
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + ex.getMessage());
    }

    // Helper privado que arma la respuesta de error estándar
    // Todos los errores tienen el mismo formato JSON
    private ResponseEntity<Map<String, Object>> buildError(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);

        return ResponseEntity.status(status).body(response);
    }


    // manejo específico para errores de validación en endpoints de auth (login y register)
    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<Map<String,String>> handleValidationErrors(
            MethodArgumentNotValidException exception
    ) {
        // extraigo los errores de validación y los pongo en un Map con el formato { "email": "el email es obligatorio", "password": "la contraseña es obligatoria" }
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(),error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // manejo específico para errores de credenciales inválidas en login,
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String,String>> handleBadCredentials(
            BadCredentialsException exception
    ){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)// 401
                .body(Map.of("error", "Email o contraseña incorrectos"));
    }
}
