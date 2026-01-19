package com.power.backend.modules.mensajecontacto.controller;

import com.power.backend.modules.mensajecontacto.model.MensajeContacto;
import com.power.backend.modules.mensajecontacto.repository.MensajeContactoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
public class MensajeContactoController {

    private final MensajeContactoRepository mensajeContactoRepository;

    public MensajeContactoController(MensajeContactoRepository mensajeContactoRepository) {
        this.mensajeContactoRepository = mensajeContactoRepository;
    }

    @GetMapping
    public List<MensajeContacto> listar() {
        return mensajeContactoRepository.findAll();
    }
}
