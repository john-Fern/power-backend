package com.power.backend.modules.producto.repository;

import com.power.backend.modules.producto.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria_Id(Integer idCategoria);
}
