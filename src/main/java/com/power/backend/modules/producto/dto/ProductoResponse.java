package com.power.backend.modules.producto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductoResponse(
                Integer id,
                String nombre,
                String descripcion,
                String imagenUrl,
                BigDecimal precio,
                Integer stock,
                Boolean destacado,
                Boolean activo,
                LocalDateTime fechaCreacion,
                Integer idCategoria,
                String nombreCategoria) {
}
