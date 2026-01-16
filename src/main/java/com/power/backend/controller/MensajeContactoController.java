package com.power.backend.controller;

import com.power.backend.entity.MensajeContacto;
import com.power.backend.repository.MensajeContactoRepository;
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
