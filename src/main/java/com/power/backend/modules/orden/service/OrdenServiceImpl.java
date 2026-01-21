package com.power.backend.modules.orden.service;

import com.power.backend.exception.BadRequestException;
import com.power.backend.exception.NotFoundException;
import com.power.backend.modules.direccion.model.Direccion;
import com.power.backend.modules.direccion.repository.DireccionRepository;
import com.power.backend.modules.orden.dto.OrdenRequest;
import com.power.backend.modules.orden.dto.OrdenResponse;
import com.power.backend.modules.orden.mapper.OrdenMapper;
import com.power.backend.modules.orden.model.Orden;
import com.power.backend.modules.orden.repository.OrdenRepository;
import com.power.backend.modules.producto.model.Producto;
import com.power.backend.modules.producto.repository.ProductoRepository;
import com.power.backend.modules.usuario.model.Usuario;
import com.power.backend.modules.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdenServiceImpl implements OrdenService {

    private final OrdenRepository repository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final DireccionRepository direccionRepository;
    private final OrdenMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenResponse> listarPorUsuario(Integer idUsuario) {
        return repository.findByUsuario_Id(idUsuario).stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenResponse> listarTodos() {
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrdenResponse obtenerPorId(Integer id) {
        Orden o = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Orden no encontrada"));
        return mapper.toResponse(o);
    }

    @Override
    @Transactional
    public OrdenResponse crear(OrdenRequest request) {
        // Validate User
        Usuario usuario = usuarioRepository.findById(request.idUsuario())
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado")); // Using NotFound as it's a relation

        // Validate Product
        Producto producto = productoRepository.findById(request.idProducto())
                .orElseThrow(() -> new NotFoundException("Producto no encontrado"));

        // Validate Quantity vs Stock?
        // Logic for stock check could be here.
        if (producto.getStock() < request.cantidad()) {
            throw new BadRequestException("No hay suficiente stock");
        }

        // Calculate Totals
        BigDecimal precioUnitario = producto.getPrecio();
        BigDecimal total = precioUnitario.multiply(BigDecimal.valueOf(request.cantidad()));

        // Validate Address
        Direccion direccion = null;
        if (request.idDireccionEnvio() != null) {
            direccion = direccionRepository.findById(request.idDireccionEnvio())
                    .orElseThrow(() -> new NotFoundException("Dirección de envío no encontrada"));
        }

        Orden entity = mapper.toEntity(request, usuario, producto, direccion, precioUnitario, total);

        // Update stock?
        producto.setStock(producto.getStock() - request.cantidad());
        productoRepository.save(producto);

        return mapper.toResponse(repository.save(entity));
    }
}
