package com.power.backend.modules.orden.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrdenResponse(
        Integer id,
        Integer idUsuario,
        Integer idProducto,
        String nombreProducto,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal total,
        String estado,
        LocalDateTime fechaOrden,
        Integer idDireccionEnvio) {
}
