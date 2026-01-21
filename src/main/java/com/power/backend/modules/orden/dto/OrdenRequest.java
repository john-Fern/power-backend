package com.power.backend.modules.orden.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import lombok.Builder;

@Builder
public record OrdenRequest(
                @NotNull(message = "El idUsuario es obligatorio") Integer idUsuario,

                @NotNull(message = "El idProducto es obligatorio") Integer idProducto,

                @NotNull(message = "La cantidad es obligatoria") @Positive(message = "La cantidad debe ser mayor a 0") Integer cantidad,

                Integer idDireccionEnvio // Optional
) {
}
