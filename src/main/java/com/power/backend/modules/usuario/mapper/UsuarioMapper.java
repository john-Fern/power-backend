package com.power.backend.modules.usuario.mapper;

import com.power.backend.modules.usuario.dto.UsuarioRequest;
import com.power.backend.modules.usuario.dto.UsuarioResponse;
import com.power.backend.modules.usuario.model.RolUsuario;
import com.power.backend.modules.usuario.model.Usuario;

import java.time.LocalDateTime;

public class UsuarioMapper {

    public static Usuario toNewEntity(UsuarioRequest request, String hashPassword) {
        return Usuario.builder()
                .nombre(request.nombre())
                .apellido(request.apellido())
                .email(request.email())
                .hashPassword(hashPassword)
                .rol(request.rol() != null ? request.rol() : RolUsuario.USUARIO)
                .fechaRegistro(LocalDateTime.now())
                .activo(true)
                .build();
    }

    public static UsuarioResponse toResponse(Usuario entity) {
        return new UsuarioResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getApellido(),
                entity.getEmail(),
                entity.getRol(),
                entity.getFechaRegistro(),
                entity.getActivo());
    }

    public static void applyUpdate(Usuario entity, UsuarioRequest request, String newHashPassword) {
        if (request.nombre() != null)
            entity.setNombre(request.nombre());
        if (request.apellido() != null)
            entity.setApellido(request.apellido());
        if (request.email() != null)
            entity.setEmail(request.email());
        if (newHashPassword != null)
            entity.setHashPassword(newHashPassword);
        if (request.rol() != null)
            entity.setRol(request.rol());
        // activo usually handled by separate method, but if needed:
        // if (request.activo() != null) entity.setActivo(request.activo());
    }
}
