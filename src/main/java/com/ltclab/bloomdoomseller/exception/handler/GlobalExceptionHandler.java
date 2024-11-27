package com.ltclab.bloomdoomseller.exception.handler;

import com.ltclab.bloomdoomseller.exception.*;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(annotations = RestController.class)

public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorMessage> handleNotFoundException(NotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public final ResponseEntity<ErrorMessage> handleAlreadyExistsException(AlreadyExistsException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(UniqueFieldException.class)
    public ResponseEntity<String> handleUniqueFieldException(UniqueFieldException e, WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(InvalidSubcategoryException.class)
    public ResponseEntity<ErrorMessage> handleInvalidSubcategoryException(InvalidSubcategoryException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(PasswordValidationException.class)
    public ResponseEntity<ErrorMessage> handlePasswordValidationException(PasswordValidationException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(TypeValidationException.class)
    public ResponseEntity<ErrorMessage> handleTypeValidationException(TypeValidationException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTokenException(InvalidTokenException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(InvalidTypeException.class)
    public ResponseEntity<ErrorMessage> handleInvalidTypeException(InvalidTypeException e, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(LocalDateTime.now(), e.getMessage(), request.getDescription(false));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
