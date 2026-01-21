package com.power.backend.modules.direccion.dto;

public record DireccionResponse(
        Integer id,
        Integer idUsuario,
        String calle,
        String numero,
        String ciudad,
        String region,
        String pais,
        String codigoPostal,
        Boolean esPrincipal) {
}
