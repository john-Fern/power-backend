package com.power.backend.repository;

import com.power.backend.entity.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Integer> {
    List<Direccion> findByUsuario_Id(Integer idUsuario);
}
