package com.power.backend.modules.mensajecontacto.mapper;

import com.power.backend.modules.mensajecontacto.dto.MensajeContactoRequest;
import com.power.backend.modules.mensajecontacto.dto.MensajeContactoResponse;
import com.power.backend.modules.mensajecontacto.model.MensajeContacto;
import com.power.backend.modules.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class MensajeContactoMapper {

    public MensajeContacto toEntity(MensajeContactoRequest request, Usuario usuario) {
        if (request == null)
            return null;
        return MensajeContacto.builder()
                .usuario(usuario)
                .nombre(request.nombre())
                .email(request.email())
                .motivo(request.motivo())
                .mensaje(request.mensaje())
                .aceptaPrivacidad(request.aceptaPrivacidad())
                .build();
    }

    public MensajeContactoResponse toResponse(MensajeContacto entity) {
        if (entity == null)
            return null;
        return new MensajeContactoResponse(
                entity.getId(),
                entity.getUsuario() != null ? entity.getUsuario().getId() : null,
                entity.getNombre(),
                entity.getEmail(),
                entity.getMotivo(),
                entity.getMensaje(),
                entity.getAceptaPrivacidad(),
                entity.getFechaEnvio());
    }
}
