package com.power.backend.modules.mensajecontacto.model;

import com.power.backend.modules.usuario.model.Usuario;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "mensajes_contacto")
public class MensajeContacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contacto")
    private Integer id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "nombre", nullable = false, length = 120)
    private String nombre;

    @Column(name = "email", nullable = false, length = 120)
    private String email;

    @Column(name = "motivo", nullable = false, length = 100)
    private String motivo;

    @Column(name = "mensaje", nullable = false, length = 1000)
    private String mensaje;

    @Column(name = "acepta_privacidad", nullable = false)
    private Boolean aceptaPrivacidad;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;

    @PrePersist
    public void prePersist() {
        if (fechaEnvio == null)
            fechaEnvio = LocalDateTime.now();
    }
}
