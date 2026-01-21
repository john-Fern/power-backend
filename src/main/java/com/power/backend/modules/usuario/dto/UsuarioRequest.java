package com.power.backend.modules.usuario.dto;

import com.power.backend.modules.usuario.model.RolUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,

        @NotBlank(message = "El apellido es obligatorio") @Size(max = 80) String apellido,

        @NotBlank(message = "El email es obligatorio") @Email @Size(max = 120) String email,

        @NotBlank(message = "La contraseña es obligatoria") @Size(min = 6, max = 100) String password,

        RolUsuario rol) {
}
