package com.power.backend.modules.direccion.mapper;

import com.power.backend.modules.direccion.dto.DireccionRequest;
import com.power.backend.modules.direccion.dto.DireccionResponse;
import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.usuario.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class DireccionMapper {

    public Direccion toEntity(DireccionRequest request, Usuario usuario) {
        if (request == null)
            return null;
        return Direccion.builder()
                .usuario(usuario)
                .calle(request.calle())
                .numero(request.numero())
                .ciudad(request.ciudad())
                .region(request.region())
                .pais(request.pais())
                .codigoPostal(request.codigoPostal())
                .esPrincipal(request.esPrincipal() != null ? request.esPrincipal() : false)
                .build();
    }

    public DireccionResponse toResponse(Direccion entity) {
        if (entity == null)
            return null;
        return new DireccionResponse(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getCalle(),
                entity.getNumero(),
                entity.getCiudad(),
                entity.getRegion(),
                entity.getPais(),
                entity.getCodigoPostal(),
                entity.getEsPrincipal());
    }

    public void updateEntity(Direccion entity, DireccionRequest request) {
        if (request.calle() != null)
            entity.setCalle(request.calle());
        if (request.numero() != null)
            entity.setNumero(request.numero());
        if (request.ciudad() != null)
            entity.setCiudad(request.ciudad());
        if (request.region() != null)
            entity.setRegion(request.region());
        if (request.pais() != null)
            entity.setPais(request.pais());
        if (request.codigoPostal() != null)
            entity.setCodigoPostal(request.codigoPostal());
        if (request.esPrincipal() != null)
            entity.setEsPrincipal(request.esPrincipal());
    }
}
