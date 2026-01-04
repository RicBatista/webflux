package br.com.jrnb.webflux.service.exception;

public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(final String message) {
        super(message);
    }
}
