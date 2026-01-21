package com.power.backend.modules.direccion.service;

import com.power.backend.exception.NotFoundException;
import com.power.backend.modules.direccion.dto.DireccionRequest;
import com.power.backend.modules.direccion.dto.DireccionResponse;
import com.power.backend.modules.direccion.mapper.DireccionMapper;
import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.direccion.repository.DireccionRepository;
import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final DireccionMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<DireccionResponse> listarPorUsuario(Integer idUsuario) {
        return repository.findByUsuario_Id(idUsuario).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<DireccionResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DireccionResponse obtenerPorId(Integer id) {
        Direccion d = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada"));
        return mapper.toResponse(d);
    }

    @Override
    @Transactional
    public DireccionResponse crear(DireccionRequest request) {
        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));

        Direccion entity = mapper.toEntity(request, usuario);
        // Logic check: if principal, unset others?
        if (Boolean.TRUE.equals(request.esPrincipal())) {
            unsetPrincipal(usuario.getId());
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public DireccionResponse actualizar(Integer id, DireccionRequest request) {
        Direccion entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada"));

        mapper.updateEntity(entity, request);

        if (Boolean.TRUE.equals(request.esPrincipal())) {
            unsetPrincipal(entity.getUsuario().getId());
            entity.setEsPrincipal(true); // Re-set in case unsetPrincipal cleared it (logic depends on impl)
        }
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Dirección no encontrada");
        repository.deleteById(id);
    }

    private void unsetPrincipal(Integer userId) {
        List<Direccion> direcciones = repository.findByUsuario_Id(userId);
        for (Direccion d : direcciones) {
            if (Boolean.TRUE.equals(d.getEsPrincipal())) {
                d.setEsPrincipal(false);
                repository.save(d);
            }
        }
    }
}
