package com.fulljob.api.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Esta clase se encarga de atrapar errores que se produzcan en cualquier parte de la API
 * (por ejemplo: login fallido, datos inválidos, recursos no encontrados, etc.)
 * y devolver una respuesta clara al cliente (como Postman o el frontend).
 *
 * En lugar de que salte una excepción fea por consola o devuelva un HTML raro,
 * aquí preparamos una respuesta en formato JSON con un mensaje claro y el código de estado.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Cuando en el código se lanza una ResponseStatusException, como por ejemplo:
     * throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró el usuario");
     * este método captura el error y lo devuelve formateado.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        return buildSimpleResponse(ex.getReason(), ex.getStatusCode().value());
    }

    /**
     * Cuando Spring Security lanza un error porque no encuentra un usuario en el login.
     * Ejemplo: throw new UsernameNotFoundException("Usuario no existe");
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        return buildSimpleResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
    }

    /**
     * Si alguien intenta hacer login con usuario/contraseña incorrectos,
     * este método captura el error y devuelve una respuesta más clara.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return buildDetailedResponse("Credenciales incorrectas", "Acceso denegado", HttpStatus.UNAUTHORIZED.value());
    }

    /**
     * Este se activa cuando usas @Valid en un DTO y algún campo no cumple lo requerido.
     * Devuelve un listado de los campos que fallaron junto con su mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Error de validación");
        response.put("errors", errors);
        response.put("status", HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Por si ocurre un error inesperado que no hemos controlado con los otros métodos,
     * este método captura el error y devuelve un mensaje genérico.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return buildDetailedResponse("Ocurrió un error inesperado", ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    // Métodos auxiliares para construir las respuestas

    /**
     * Crea una respuesta simple con mensaje y código de estado.
     * Útil cuando no necesitas dar detalles adicionales.
     */
    private ResponseEntity<Map<String, Object>> buildSimpleResponse(String message, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status);
        return ResponseEntity.status(status).body(response);
    }

    /**
     * Crea una respuesta con mensaje + detalle + código de estado.
     * Útil cuando quieres explicar un poco más lo que ha pasado.
     */
    private ResponseEntity<Map<String, Object>> buildDetailedResponse(String message, String error, int status) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("error", error);
        response.put("status", status);
        return ResponseEntity.status(status).body(response);
    }
}
