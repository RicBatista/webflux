package br.com.jrnb.webflux.model.request;

import java.io.Serializable;

/**
 * DTO for {@link br.com.jrnb.webflux.entity.User}
 */
public record UserRequest(String name, String email, String password) implements Serializable {
}