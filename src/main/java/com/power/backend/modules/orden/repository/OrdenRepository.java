package com.power.backend.modules.orden.repository;

import com.power.backend.modules.orden.model.Orden;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdenRepository extends JpaRepository<Orden, Integer> {
    List<Orden> findByUsuario_Id(Integer idUsuario);
}
