package com.power.backend.modules.categoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Builder;

@Builder
public record CategoriaRequest(
                @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,

                @Size(max = 255) String descripcion) {
}
