package com.power.backend.modules.direccion.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Builder;

@Builder
public record DireccionRequest(
                @NotNull(message = "El idUsuario es obligatorio") Integer idUsuario,

                @NotBlank(message = "La calle es obligatoria") @Size(max = 120) String calle,

                @NotBlank(message = "El número es obligatorio") @Size(max = 20) String numero,

                @NotBlank(message = "La ciudad es obligatoria") @Size(max = 80) String ciudad,

                @NotBlank(message = "La región es obligatoria") @Size(max = 80) String region,

                @NotBlank(message = "El país es obligatorio") @Size(max = 80) String pais,

                @Size(max = 20) String codigoPostal,

                Boolean esPrincipal) {
}
