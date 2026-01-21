package com.power.backend.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Builder;

@Builder
public record AuthRequest(
                @NotBlank(message = "El email es obligatorio") @Email(message = "Debe ser un email válido") String email,

                @NotBlank(message = "La contraseña es obligatoria") String password) {
}
