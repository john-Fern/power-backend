package com.power.backend.modules.producto.service;

import com.power.backend.modules.producto.dto.ProductoRequest;
import com.power.backend.modules.categoria.model.Categoria;
import com.power.backend.modules.producto.model.Producto;
import com.power.backend.modules.categoria.repository.CategoriaRepository;
import com.power.backend.modules.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;


    public ProductoService(
            ProductoRepository productoRepository,
            CategoriaRepository categoriaRepository
    ) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public Producto crearProducto(ProductoRequest request) {

        Categoria categoria = categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        Producto producto = new Producto();
        producto.setNombre(request.getNombre());
        producto.setDescripcion(request.getDescripcion());
        producto.setPrecio(request.getPrecio());
        producto.setStock(request.getStock());
        producto.setActivo(request.isActivo());
        producto.setDestacado(request.isEsDestacado());
        producto.setFechaCreacion(LocalDateTime.now());
        producto.setCategoria(categoria);

        return productoRepository.save(producto);
    }
}
