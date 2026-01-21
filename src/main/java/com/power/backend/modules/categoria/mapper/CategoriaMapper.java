package com.power.backend.modules.categoria.mapper;

import com.power.backend.modules.categoria.dto.CategoriaRequest;
import com.power.backend.modules.categoria.dto.CategoriaResponse;
import com.power.backend.modules.categoria.model.Categoria;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {

    public Categoria toEntity(CategoriaRequest request) {
        if (request == null)
            return null;
        return Categoria.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .build();
    }

    public CategoriaResponse toResponse(Categoria category) {
        if (category == null)
            return null;
        return new CategoriaResponse(
                category.getId(),
                category.getNombre(),
                category.getDescripcion());
    }

    public void updateEntity(Categoria existing, CategoriaRequest request) {
        if (request.nombre() != null)
            existing.setNombre(request.nombre());
        if (request.descripcion() != null)
            existing.setDescripcion(request.descripcion());
    }
}
