package com.power.backend.modules.mensajecontacto.service;

import com.power.backend.modules.mensajecontacto.dto.MensajeContactoRequest;
import com.power.backend.modules.mensajecontacto.dto.MensajeContactoResponse;
import com.power.backend.modules.mensajecontacto.mapper.MensajeContactoMapper;
import com.power.backend.modules.mensajecontacto.model.MensajeContacto;
import com.power.backend.modules.mensajecontacto.repository.MensajeContactoRepository;
import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MensajeContactoServiceImpl implements MensajeContactoService {

    private final MensajeContactoRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final MensajeContactoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MensajeContactoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public MensajeContactoResponse crear(MensajeContactoRequest request) {
        Usuario usuario = null;
        if (request.idUsuario() != null) {
            usuario = usuarioRepository.findById(request.idUsuario())
                    .orElse(null); // Optional user, if not found treat as guest? or throwing error?
                                   // Requirement didn't specify. Assuming optional so null is okay if not found or
                                   // id not provided.
                                   // Actually code says "id_usuario" nullable in DB (implied by optional=true in
                                   // code).
                                   // But if id provided but not found? I'll let it be null.
        }

        MensajeContacto entity = mapper.toEntity(request, usuario);
        return mapper.toResponse(repository.save(entity));
    }
}
