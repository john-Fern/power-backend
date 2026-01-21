package com.power.backend.modules.categoria.repository;

import com.power.backend.modules.categoria.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    java.util.Optional<Categoria> findByNombre(String nombre);
}
