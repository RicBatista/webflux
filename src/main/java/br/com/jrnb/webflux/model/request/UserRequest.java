package br.com.jrnb.webflux.model.request;

import br.com.jrnb.webflux.validator.TrimString;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO for {@link br.com.jrnb.webflux.entity.User}
 */
public record UserRequest(

        @TrimString
        @Size(min = 3, max = 70, message = "Must be between 3 and 70")
        @NotBlank(message = "Must not be null")
        String name,

        @TrimString
        @Email(message = "Invalid!")
        String email,

        @TrimString
        @Size(min = 3, max = 10, message = "Must be between 3 and 10")
        @NotBlank(message = "Must not be null")
        String password)
{
}