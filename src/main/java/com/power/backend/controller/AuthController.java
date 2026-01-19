package com.power.backend.controller;

import com.power.backend.modules.usuario.dto.LoginRequest;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import com.power.backend.modules.usuario.dto.RegisterRequest;
import com.power.backend.modules.usuario.model.Usuario;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        var userOpt = usuarioRepository.findByEmail(req.getEmail());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        var user = userOpt.get();
        if (!passwordEncoder.matches(req.getPassword(), user.getHashPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales inválidas");
        }

        // Sin JWT todavía: respuesta mínima para el front
        return ResponseEntity.ok(Map.of(
                "message", "Login OK",
                "idUsuario", user.getId(),
                "email", user.getEmail(),
                "nombre", user.getNombre(),
                "apellido", user.getApellido()
        ));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {

        // Validaciones mínimas
        if (req.getEmail() == null || req.getEmail().isBlank()
                || req.getPassword() == null || req.getPassword().isBlank()
                || req.getNombre() == null || req.getNombre().isBlank()
                || req.getApellido() == null || req.getApellido().isBlank()) {
            return ResponseEntity.badRequest().body("Faltan campos obligatorios");
        }

        // Email único
        if (usuarioRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El email ya está registrado");
        }

        Usuario u = new Usuario();
        u.setNombre(req.getNombre());
        u.setApellido(req.getApellido());
        u.setEmail(req.getEmail());
        u.setHashPassword(passwordEncoder.encode(req.getPassword())); // BCrypt
        u.setFechaRegistro(LocalDateTime.now());
        u.setActivo(true);

        Usuario guardado = usuarioRepository.save(u);

        // Respuesta segura: NO devolvemos el hash
        return ResponseEntity.status(HttpStatus.CREATED).body(
                java.util.Map.of(
                        "idUsuario", guardado.getId(),
                        "email", guardado.getEmail(),
                        "nombre", guardado.getNombre(),
                        "apellido", guardado.getApellido()
                )
        );
    }
}
