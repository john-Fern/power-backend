package com.power.backend.controller;

import com.power.backend.dto.ProductoRequest;
import com.power.backend.entity.Producto;
import com.power.backend.repository.ProductoRepository;
import com.power.backend.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.power.backend.repository.CategoriaRepository;
import com.power.backend.dto.ProductoCreateDTO;
import org.springframework.http.*;

import java.time.LocalDateTime;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoService productoService;

    public ProductoController(ProductoRepository productoRepository, CategoriaRepository categoriaRepository, ProductoService productoService) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
        this.productoService = productoService;
    }

    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) Integer idCategoria) {
        if (idCategoria != null) {
            return productoRepository.findByCategoria_Id(idCategoria);
        }
        return productoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(
            @Valid @RequestBody ProductoRequest request
    ) {
        Producto producto = productoService.crearProducto(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(producto);
    }


}
