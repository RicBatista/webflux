package br.com.jrnb.webflux.controller.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final List<FieldError> fieldErrors = new ArrayList<>();

    ValidationError(LocalDateTime timestamp, String path, String message, String error) {
        super(timestamp, path, message, error);
    }


    public void   addFieldError(String fieldError, String message) {
        this.fieldErrors.add(new FieldError(fieldError, message));
    }

    @Getter
    @AllArgsConstructor
    private static final class FieldError{
        private String field;
        private String message;
    }
}


