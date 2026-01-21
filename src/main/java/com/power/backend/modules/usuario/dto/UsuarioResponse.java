package com.power.backend.modules.usuario.dto;

import com.power.backend.modules.usuario.model.RolUsuario;
import java.time.LocalDateTime;

public record UsuarioResponse(
                Integer id,
                String nombre,
                String apellido,
                String email,
                RolUsuario rol,
                LocalDateTime fechaRegistro,
                Boolean activo) {
}
