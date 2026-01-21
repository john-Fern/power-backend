package com.power.backend.modules.direccion.controller;

import com.power.backend.modules.direccion.dto.DireccionRequest;
import com.power.backend.modules.direccion.dto.DireccionResponse;
import com.power.backend.modules.direccion.service.DireccionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/direcciones")
@RequiredArgsConstructor
public class DireccionController {

    private final DireccionService service;

    @GetMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or (#idUsuario != null and @userSecurity.isCurrentUser(authentication, #idUsuario))")
    public ResponseEntity<List<DireccionResponse>> listar(@RequestParam(required = false) Integer idUsuario) {
        if (idUsuario != null) {
            return ResponseEntity.ok(service.listarPorUsuario(idUsuario));
        }
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DireccionResponse> obtenerPorId(@PathVariable Integer id,
            org.springframework.security.core.Authentication auth) {
        DireccionResponse response = service.obtenerPorId(id);
        checkOwnership(auth, response.idUsuario());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN') or @userSecurity.isCurrentUser(authentication, #request.idUsuario)")
    public ResponseEntity<DireccionResponse> crear(@Valid @RequestBody DireccionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DireccionResponse> actualizar(@PathVariable Integer id,
            @Valid @RequestBody DireccionRequest request, org.springframework.security.core.Authentication auth) {
        DireccionResponse existing = service.obtenerPorId(id);
        checkOwnership(auth, existing.idUsuario());
        return ResponseEntity.ok(service.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id,
            org.springframework.security.core.Authentication auth) {
        DireccionResponse existing = service.obtenerPorId(id);
        checkOwnership(auth, existing.idUsuario());
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private void checkOwnership(org.springframework.security.core.Authentication auth, Integer userId) {
        if (auth != null && auth.getPrincipal() instanceof com.power.backend.security.model.UsuarioPrincipal user) {
            boolean isAdmin = user.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            if (!isAdmin && !user.getUsuario().getId().equals(userId)) {
                throw new org.springframework.security.access.AccessDeniedException(
                        "No tienes permiso para acceder a este recurso");
            }
        }
    }
}
