package com.power.backend.modules.orden.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.backend.modules.categoria.dto.CategoriaRequest;
import com.power.backend.modules.categoria.service.CategoriaService;
import com.power.backend.modules.orden.dto.OrdenRequest;
import com.power.backend.modules.producto.dto.ProductoRequest;
import com.power.backend.modules.producto.service.ProductoService;
import com.power.backend.security.dto.RegisterRequest;
import com.power.backend.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class OrdenIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ProductoService productoService;

    @Test
    @WithMockUser
    void shouldCreateOrdenAndList() throws Exception {
        // 1. Setup Data: User, Category, Product
        var user = authService.register(RegisterRequest.builder()
                .nombre("Buyer").apellido("One")
                .email("buyer@test.com").password("pass123").build());

        var cat = categoriaService.save(CategoriaRequest.builder()
                .nombre("OrdenCat").descripcion("Misc").build());

        var prod = productoService.crearProducto(ProductoRequest.builder()
                .nombre("ProductToBuy")
                .precio(BigDecimal.TEN)
                .stock(100)
                .idCategoria(cat.id())
                .activo(true)
                .build());

        // 2. Create Order
        OrdenRequest req = OrdenRequest.builder()
                .idUsuario(user.idUsuario())
                .idProducto(prod.id())
                .cantidad(2)
                .build();

        mockMvc.perform(post("/api/ordenes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.total").value(20.0)); // 10 * 2

        // 3. List Orders for User
        mockMvc.perform(get("/api/ordenes?idUsuario=" + user.idUsuario()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists());
    }
}
