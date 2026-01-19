package com.power.backend.modules.direccion.repository;

import com.power.backend.modules.direccion.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByUsuario_Id(Integer idUsuario);
}
