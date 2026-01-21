package com.power.backend.modules.categoria.service;

import com.power.backend.modules.categoria.dto.CategoriaRequest;
import com.power.backend.modules.categoria.dto.CategoriaResponse;
import java.util.List;

public interface CategoriaService {
    List<CategoriaResponse> findAll();

    CategoriaResponse findById(Integer id);

    CategoriaResponse save(CategoriaRequest request);

    CategoriaResponse update(Integer id, CategoriaRequest request);

    void delete(Integer id);
}
