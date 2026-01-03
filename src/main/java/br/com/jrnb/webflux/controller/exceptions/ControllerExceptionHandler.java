package br.com.jrnb.webflux.controller.exceptions;


import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Mono;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

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

        ValidationError error = new ValidationError(now(), BAD_REQUEST.toString(), "Validation error", "Error on validation attributes");

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
}
