package com.power.backend.modules.mensajecontacto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;

@Builder
public record MensajeContactoRequest(
                Integer idUsuario, // Optional, can be null

                @NotBlank(message = "El nombre es obligatorio") @Size(max = 120) String nombre,

                @NotBlank(message = "El email es obligatorio") @Email @Size(max = 120) String email,

                @NotBlank(message = "El motivo es obligatorio") @Size(max = 100) String motivo,

                @NotBlank(message = "El mensaje es obligatorio") @Size(max = 1000) String mensaje,

                @NotNull Boolean aceptaPrivacidad) {
}
