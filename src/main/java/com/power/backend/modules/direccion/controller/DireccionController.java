package com.power.backend.modules.direccion.controller;

import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.direccion.repository.DireccionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.power.backend.modules.usuario.repository.UsuarioRepository;
import org.springframework.http.*;
import com.power.backend.modules.direccion.dto.DireccionCreateDTO;

@RestController
@RequestMapping("/api/direcciones")
public class DireccionController {

    private final DireccionRepository direccionRepository;
    private final UsuarioRepository usuarioRepository;

    public DireccionController(DireccionRepository direccionRepository, UsuarioRepository usuarioRepository) {
        this.direccionRepository = direccionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public List<Direccion> listar(@RequestParam(required = false) Integer idUsuario) {
        if (idUsuario != null) return direccionRepository.findByUsuario_Id(idUsuario);
        return direccionRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody DireccionCreateDTO dto) {

        var userOpt = usuarioRepository.findById(dto.getIdUsuario());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("idUsuario no existe");
        }

        Direccion d = new Direccion();
        d.setUsuario(userOpt.get());
        d.setCalle(dto.getCalle());
        d.setNumero(dto.getNumero());
        d.setCiudad(dto.getCiudad());
        d.setRegion(dto.getRegion());
        d.setPais(dto.getPais());
        d.setCodigoPostal(dto.getCodigoPostal());
        d.setEsPrincipal(dto.getEsPrincipal() != null ? dto.getEsPrincipal() : false);

        return ResponseEntity.status(HttpStatus.CREATED).body(direccionRepository.save(d));
    }
}
