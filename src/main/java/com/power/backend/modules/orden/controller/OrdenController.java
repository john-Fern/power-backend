package com.power.backend.modules.orden.controller;

import com.power.backend.modules.orden.dto.OrdenRequest;
import com.power.backend.modules.orden.dto.OrdenResponse;
import com.power.backend.modules.orden.service.OrdenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordenes")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenService service;

    @GetMapping
    public ResponseEntity<List<OrdenResponse>> listar(@RequestParam(required = false) Integer idUsuario) {
        if (idUsuario != null) {
            return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
        }
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody OrdenRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> obtenerPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
