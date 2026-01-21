package com.power.backend.modules.categoria.service;

import com.power.backend.exception.NotFoundException;
import com.power.backend.modules.categoria.dto.CategoriaRequest;
import com.power.backend.modules.categoria.dto.CategoriaResponse;
import com.power.backend.modules.categoria.mapper.CategoriaMapper;
import com.power.backend.modules.categoria.model.Categoria;
import com.power.backend.modules.categoria.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponse> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponse findById(Integer id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id: " + id));
    }

    @Override
    @Transactional
    public CategoriaResponse save(CategoriaRequest request) {
        Categoria entity = mapper.toEntity(request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public CategoriaResponse update(Integer id, CategoriaRequest request) {
        Categoria entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada con id: " + id));
        mapper.updateEntity(entity, request);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Categoría no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}
