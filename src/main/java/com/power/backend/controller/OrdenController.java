package com.power.backend.controller;

import com.power.backend.entity.Orden;
import com.power.backend.repository.OrdenRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.power.backend.repository.*;
import org.springframework.http.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.power.backend.dto.OrdenCreateDTO;
import com.power.backend.entity.Orden;

@RestController
@RequestMapping("/api/ordenes")
public class OrdenController {

    private final OrdenRepository ordenRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final DireccionRepository direccionRepository;

    public OrdenController(OrdenRepository ordenRepository,
                           UsuarioRepository usuarioRepository,
                           ProductoRepository productoRepository,
                           DireccionRepository direccionRepository) {
        this.ordenRepository = ordenRepository;
        this.usuarioRepository = usuarioRepository;
        this.productoRepository = productoRepository;
        this.direccionRepository = direccionRepository;
    }

    @GetMapping
    public List<Orden> listar(@RequestParam(required = false) Integer idUsuario) {
        if (idUsuario != null) return ordenRepository.findByUsuario_Id(idUsuario);
        return ordenRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody OrdenCreateDTO dto) {

        if (dto.getCantidad() == null || dto.getCantidad() <= 0) {
            return ResponseEntity.badRequest().body("cantidad debe ser > 0");
        }

        var userOpt = usuarioRepository.findById(dto.getIdUsuario());
        if (userOpt.isEmpty()) return ResponseEntity.badRequest().body("idUsuario no existe");

        var prodOpt = productoRepository.findById(dto.getIdProducto());
        if (prodOpt.isEmpty()) return ResponseEntity.badRequest().body("idProducto no existe");

        var producto = prodOpt.get();

        // calcular precios desde el backend (más confiable)
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(dto.getCantidad()));

        Orden o = new Orden();
        o.setUsuario(userOpt.get());
        o.setProducto(producto);
        o.setCantidad(dto.getCantidad());
        o.setPrecioUnitario(precioUnitario);
        o.setTotal(total);
        o.setEstado("PENDIENTE");
        o.setFechaOrden(LocalDateTime.now());

        // Dirección opcional
        if (dto.getIdDireccionEnvio() != null) {
            var dirOpt = direccionRepository.findById(dto.getIdDireccionEnvio());
            if (dirOpt.isEmpty()) return ResponseEntity.badRequest().body("idDireccionEnvio no existe");
            o.setDireccionEnvio(dirOpt.get());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(ordenRepository.save(o));
    }

}
