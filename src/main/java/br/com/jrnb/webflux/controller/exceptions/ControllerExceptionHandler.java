package br.com.jrnb.webflux.controller.exceptions;


import br.com.jrnb.webflux.service.exception.ObjectNotFoundException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    ResponseEntity<Mono<StandardError>> duplicateException(final DuplicateKeyException e, final ServerHttpRequest request) {

        return ResponseEntity.badRequest().body(Mono.just(StandardError.builder()
                .timestamp(now())
                .error(BAD_REQUEST.getReasonPhrase())
                .path(request.getPath().toString())
                .message(verifyDupKey(e.getMessage()))
                .build()));
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<Mono<StandardError>> validationError(final WebExchangeBindException e, final ServerHttpRequest request) {

        ValidationError error = new ValidationError(now(), BAD_REQUEST.toString(), "Validation error", "Error on validation attributes", 0);

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.addFieldError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(Mono.just(error));
    }


    private String verifyDupKey(String message){
        if(message.contains("email dup key")){

            return "E-mail already exists";
        }

        return "Duplicate key exception";
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public Mono<ResponseEntity<StandardError>> objectNotFoundException(
            ObjectNotFoundException ex,
            ServerHttpRequest request) {

        return Mono.just(
                        StandardError.builder()
                                .timestamp(LocalDateTime.now()) // Ou Instant.now()
                                .status(NOT_FOUND.value())
                                .error(NOT_FOUND.getReasonPhrase())
                                .message(ex.getMessage())
                                .path(request.getPath().toString())
                                .build()
                )
                .map(errorObj -> ResponseEntity
                        .status(NOT_FOUND)
                        .body(errorObj)
                );
    }
}
