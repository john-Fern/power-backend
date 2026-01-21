package com.power.backend.modules.mensajecontacto.controller;

import com.power.backend.modules.mensajecontacto.dto.MensajeContactoRequest;
import com.power.backend.modules.mensajecontacto.dto.MensajeContactoResponse;
import com.power.backend.modules.mensajecontacto.service.MensajeContactoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacto")
@RequiredArgsConstructor
public class MensajeContactoController {

    private final MensajeContactoService service;

    @GetMapping
    public ResponseEntity<List<MensajeContactoResponse>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<MensajeContactoResponse> crear(@Valid @RequestBody MensajeContactoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }
}
