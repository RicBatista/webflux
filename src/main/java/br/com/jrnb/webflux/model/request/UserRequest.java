package br.com.jrnb.webflux.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link br.com.jrnb.webflux.entity.User}
 */
public record UserRequest(
        @Size(min = 3, max = 70, message = "Must be between 3 and 70")
        @NotBlank(message = "Must not be null")
        String name,
        @Email(message = "Invalid!")
        String email,
        @Size(min = 3, max = 10, message = "Must be between 3 and 10")
        @NotBlank(message = "Must not be null")
        String password)
{
}