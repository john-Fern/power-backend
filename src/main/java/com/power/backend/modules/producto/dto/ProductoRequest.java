package com.power.backend.modules.producto.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

import lombok.Builder;

@Builder
public record ProductoRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 120) String nombre,

        @Size(max = 500) String descripcion,

        @Size(max = 255) String imagenUrl,

        @NotNull(message = "El precio es obligatorio") @Positive BigDecimal precio,

        @NotNull(message = "El stock es obligatorio") @Positive Integer stock,

        Boolean destacado,
        Boolean activo,

        @NotNull(message = "La categoría es obligatoria") Integer idCategoria) {
}
