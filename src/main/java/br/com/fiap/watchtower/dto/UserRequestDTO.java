package br.com.fiap.watchtower.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UserRequestDTO(
        @NotBlank
        String name,
        @Email
        String email,
        @Min(8)
        String password
) {
}
