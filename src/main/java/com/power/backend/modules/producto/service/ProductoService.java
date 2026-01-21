package com.power.backend.modules.producto.service;

import com.power.backend.modules.producto.dto.ProductoRequest;
import com.power.backend.modules.producto.dto.ProductoResponse;
import java.util.List;

public interface ProductoService {
    List<ProductoResponse> listarTodos();

    List<ProductoResponse> listarPorCategoria(Integer idCategoria);

    ProductoResponse obtenerPorId(Integer id);

    ProductoResponse crearProducto(ProductoRequest request);

    ProductoResponse actualizarProducto(Integer id, ProductoRequest request);

    void eliminarProducto(Integer id);
}
