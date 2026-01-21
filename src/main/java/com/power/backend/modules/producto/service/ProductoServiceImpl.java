package com.power.backend.modules.producto.service;

import com.power.backend.exception.NotFoundException;
import com.power.backend.modules.categoria.model.Categoria;
import com.power.backend.modules.categoria.repository.CategoriaRepository;
import com.power.backend.modules.producto.dto.ProductoRequest;
import com.power.backend.modules.producto.dto.ProductoResponse;
import com.power.backend.modules.producto.mapper.ProductoMapper;
import com.power.backend.modules.producto.model.Producto;
import com.power.backend.modules.producto.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repository;
    private final CategoriaRepository categoriaRepository;
    private final ProductoMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoResponse> listarPorCategoria(Integer idCategoria) {
        return repository.findByCategoria_Id(idCategoria).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Integer id) {
        Producto p = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));
        return mapper.toResponse(p);
    }

    @Override
    @Transactional
    public ProductoResponse crearProducto(ProductoRequest request) {
        Categoria categoria = categoriaRepository.findById(request.idCategoria())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));

        Producto entity = mapper.toEntity(request, categoria);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public ProductoResponse actualizarProducto(Integer id, ProductoRequest request) {
        Producto entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        Categoria categoria = null;
        if (request.idCategoria() != null) {
            categoria = categoriaRepository.findById(request.idCategoria())
                    .orElseThrow(() -> new NotFoundException("Categoría no encontrada"));
        } else {
            categoria = entity.getCategoria();
        }

        mapper.updateEntity(entity, request, categoria);
        return mapper.toResponse(repository.save(entity));
    }

    @Override
    @Transactional
    public void eliminarProducto(Integer id) {
        if (!repository.existsById(id))
            throw new NotFoundException("Producto no encontrado");
        repository.deleteById(id);
    }
}
