package com.power.backend.modules.orden.service;

import com.power.backend.modules.orden.dto.OrdenRequest;
import com.power.backend.modules.orden.dto.OrdenResponse;
import java.util.List;

public interface OrdenService {
    List<OrdenResponse> listarPorUsuario(Integer idUsuario);

    List<OrdenResponse> listarTodos();

    OrdenResponse crear(OrdenRequest request);

    OrdenResponse obtenerPorId(Integer id);
}
