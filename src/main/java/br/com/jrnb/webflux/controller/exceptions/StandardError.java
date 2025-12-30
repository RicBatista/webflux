package br.com.jrnb.webflux.controller.exceptions;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class StandardError implements Serializable {

    @Serial
    private static final long  serialVersionUID = 1L;

    private LocalDateTime timestamp;
    private String path;
    private String message;
    private HttpStatus status;
    private String error;
}
