package com.power.backend.security.service;

import com.power.backend.exception.BadRequestException;
import com.power.backend.modules.usuario.mapper.UsuarioMapper;
import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import com.power.backend.security.dto.AuthRequest;
import com.power.backend.security.dto.AuthResponse;
import com.power.backend.security.dto.RegisterRequest;
import com.power.backend.security.jwt.JwtService;
import com.power.backend.security.model.UsuarioPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.power.backend.modules.usuario.dto.UsuarioRequest;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest req) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password()));

        UsuarioPrincipal principal = (UsuarioPrincipal) authentication.getPrincipal();
        Usuario u = principal.getUsuario();
        String token = jwtService.generateToken(u.getEmail());

        return new AuthResponse(u.getId(), u.getNombre(), u.getEmail(), u.getRol(), token);
    }

    public AuthResponse register(RegisterRequest req) {
        if (usuarioRepository.existsByEmailIgnoreCase(req.email())) {
            throw new BadRequestException("El email ya está registrado");
        }

        // Reuse UsuarioMapper or manual builder
        UsuarioRequest uReq = new UsuarioRequest(
                req.nombre(), req.apellido(), req.email(), req.password(), null);
        String hash = passwordEncoder.encode(req.password());
        Usuario u = UsuarioMapper.toNewEntity(uReq, hash);

        Usuario guardado = usuarioRepository.save(u);
        String token = jwtService.generateToken(guardado.getEmail());

        return new AuthResponse(guardado.getId(), guardado.getNombre(), guardado.getEmail(), guardado.getRol(), token);
    }
}
