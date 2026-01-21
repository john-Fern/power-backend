package com.power.backend.modules.mensajecontacto.dto;

import java.time.LocalDateTime;

public record MensajeContactoResponse(
        Integer id,
        Integer idUsuario,
        String nombre,
        String email,
        String motivo,
        String mensaje,
        Boolean aceptaPrivacidad,
        LocalDateTime fechaEnvio) {
}
