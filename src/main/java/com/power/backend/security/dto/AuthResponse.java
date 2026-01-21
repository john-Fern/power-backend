package com.power.backend.security.dto;

import com.power.backend.modules.usuario.model.RolUsuario;

public record AuthResponse(
        Integer idUsuario,
        String nombre,
        String email,
        RolUsuario rol,
        String token) {
}
