package br.com.jrnb.webflux.model.response;

import java.io.Serializable;

/**
 * DTO for {@link br.com.jrnb.webflux.entity.User}
 */
public record UserResponse(String id, String name, String email, String password) implements Serializable {
}