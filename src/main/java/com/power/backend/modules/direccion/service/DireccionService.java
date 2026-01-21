package com.power.backend.modules.direccion.service;

import com.power.backend.modules.direccion.dto.DireccionRequest;
import com.power.backend.modules.direccion.dto.DireccionResponse;
import java.util.List;

public interface DireccionService {
    List<DireccionResponse> listarPorUsuario(Integer idUsuario);

    List<DireccionResponse> listarTodos();

    DireccionResponse obtenerPorId(Integer id);

    DireccionResponse crear(DireccionRequest request);

    DireccionResponse actualizar(Integer id, DireccionRequest request);

    void eliminar(Integer id);
}
