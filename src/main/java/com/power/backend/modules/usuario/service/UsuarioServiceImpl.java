package com.power.backend.modules.usuario.service;

import com.power.backend.modules.usuario.dto.UsuarioRequest;
import com.power.backend.modules.usuario.dto.UsuarioResponse;
import com.power.backend.exception.BadRequestException;
import com.power.backend.exception.NotFoundException;
import com.power.backend.modules.usuario.mapper.UsuarioMapper;
import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UsuarioResponse crear(UsuarioRequest request) {
        if (isBlank(request.nombre()) || isBlank(request.email()) || isBlank(request.password())) {
            throw new BadRequestException("Los campos nombre, email y contrasena son obligatorios");
        }

        if (repository.existsByEmailIgnoreCase(request.email())) {
            throw new BadRequestException("El email ya existe");
        }

        String hash = passwordEncoder.encode(request.password());
        Usuario entity = UsuarioMapper.toNewEntity(request, hash);
        return UsuarioMapper.toResponse(repository.save(entity));
    }

    @Override
    public UsuarioResponse obtenerPorId(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        return UsuarioMapper.toResponse(usuario);
    }

    @Override
    public List<UsuarioResponse> listar() {
        return repository.findAll().stream().map(UsuarioMapper::toResponse).toList();
    }

    @Override
    public UsuarioResponse actualizar(Integer id, UsuarioRequest request) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        if (request.email() != null && !request.email().equalsIgnoreCase(usuario.getEmail())) {
            if (repository.existsByEmailIgnoreCase(request.email())) {
                throw new BadRequestException("El email ya existe");
            }
        }

        String newHash = null;
        if (request.password() != null && !request.password().isBlank()) {
            newHash = passwordEncoder.encode(request.password());
        }

        UsuarioMapper.applyUpdate(usuario, request, newHash);
        return UsuarioMapper.toResponse(repository.save(usuario));
    }

    @Override
    public void eliminar(Integer id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Usuario no encontrado");
        repository.deleteById(id);
    }

    @Override
    public void desactivar(Integer id) {
        Usuario usuario = repository.findById(id).orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        usuario.setActivo(false);
        repository.save(usuario);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }
}
