package com.power.backend.security.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;

@Builder
public record RegisterRequest(
                @NotBlank(message = "El nombre es obligatorio") String nombre,

                @NotBlank(message = "El apellido es obligatorio") String apellido,

                @NotBlank(message = "El email es obligatorio") @Email String email,

                @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres") String password) {
}
