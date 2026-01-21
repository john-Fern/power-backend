package com.power.backend.modules.orden.mapper;

import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.orden.dto.OrdenRequest;
import com.power.backend.modules.orden.dto.OrdenResponse;
import com.power.backend.modules.orden.model.Orden;
import com.power.backend.modules.producto.model.Producto;
import com.power.backend.modules.usuario.model.Usuario;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrdenMapper {

    public Orden toEntity(OrdenRequest request, Usuario usuario, Producto producto, Direccion direccion,
            BigDecimal precioUnitario, BigDecimal total) {
        if (request == null)
            return null;
        return Orden.builder()
                .usuario(usuario)
                .producto(producto)
                .cantidad(request.cantidad())
                .precioUnitario(precioUnitario)
                .total(total)
                .direccionEnvio(direccion)
                // fechaOrden and estado set by PrePersist or Service
                .build();
    }

    public OrdenResponse toResponse(Orden entity) {
        if (entity == null)
            return null;
        return new OrdenResponse(
                entity.getId(),
                entity.getUsuario().getId(),
                entity.getProducto().getId(),
                entity.getProducto().getNombre(),
                entity.getCantidad(),
                entity.getPrecioUnitario(),
                entity.getTotal(),
                entity.getEstado(),
                entity.getFechaOrden(),
                entity.getDireccionEnvio() != null ? entity.getDireccionEnvio().getId() : null);
    }
}
