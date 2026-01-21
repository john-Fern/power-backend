package com.power.backend.modules.producto.mapper;

import com.power.backend.modules.categoria.model.Categoria;
import com.power.backend.modules.producto.dto.ProductoRequest;
import com.power.backend.modules.producto.dto.ProductoResponse;
import com.power.backend.modules.producto.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class ProductoMapper {

    public Producto toEntity(ProductoRequest request, Categoria categoria) {
        if (request == null)
            return null;
        return Producto.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .imagenUrl(request.imagenUrl())
                .precio(request.precio())
                .stock(request.stock())
                .destacado(request.destacado())
                .activo(request.activo())
                .categoria(categoria)
                .build();
    }

    public ProductoResponse toResponse(Producto entity) {
        if (entity == null)
            return null;
        return new ProductoResponse(
                entity.getId(),
                entity.getNombre(),
                entity.getDescripcion(),
                entity.getImagenUrl(),
                entity.getPrecio(),
                entity.getStock(),
                entity.getDestacado(),
                entity.getActivo(),
                entity.getFechaCreacion(),
                entity.getCategoria() != null ? entity.getCategoria().getId() : null,
                entity.getCategoria() != null ? entity.getCategoria().getNombre() : null);
    }

    public void updateEntity(Producto entity, ProductoRequest request, Categoria categoria) {
        if (request.nombre() != null)
            entity.setNombre(request.nombre());
        if (request.descripcion() != null)
            entity.setDescripcion(request.descripcion());
        if (request.imagenUrl() != null)
            entity.setImagenUrl(request.imagenUrl());
        if (request.precio() != null)
            entity.setPrecio(request.precio());
        if (request.stock() != null)
            entity.setStock(request.stock());
        if (request.destacado() != null)
            entity.setDestacado(request.destacado());
        if (request.activo() != null)
            entity.setActivo(request.activo());
        if (categoria != null)
            entity.setCategoria(categoria);
    }
}
