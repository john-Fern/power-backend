package com.power.backend.modules.mensajecontacto.service;

import com.power.backend.modules.mensajecontacto.dto.MensajeContactoRequest;
import com.power.backend.modules.mensajecontacto.dto.MensajeContactoResponse;
import java.util.List;

public interface MensajeContactoService {
    List<MensajeContactoResponse> listarTodos();

    MensajeContactoResponse crear(MensajeContactoRequest request);
}
