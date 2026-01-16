package com.power.backend.controller;

import com.power.backend.entity.Categoria;
import com.power.backend.repository.CategoriaRepository;
import org.springframework.web.bind.annotation.*;
import com.power.backend.dto.CategoriaCreateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping
    public List<Categoria> listar() {
        return categoriaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Categoria> crear(@RequestBody CategoriaCreateDTO dto) {
        Categoria c = new Categoria();
        c.setNombre(dto.getNombre());
        c.setDescripcion(dto.getDescripcion());

        Categoria guardada = categoriaRepository.save(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

}
