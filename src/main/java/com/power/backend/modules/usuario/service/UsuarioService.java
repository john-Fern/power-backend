package com.power.backend.modules.usuario.service;
import com.power.backend.modules.usuario.dto.UsuarioRequest;
import com.power.backend.modules.usuario.dto.UsuarioResponse;

import java.util.List;

public interface UsuarioService {
    UsuarioResponse crear(UsuarioRequest request);
    UsuarioResponse obtenerPorId(Integer id);
    List<UsuarioResponse> listar();
    UsuarioResponse actualizar(Integer id, UsuarioRequest request);
    void eliminar(Integer id);
    void desactivar(Integer id);
}
