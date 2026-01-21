package com.power.backend.modules.producto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.backend.modules.categoria.dto.CategoriaRequest;
import com.power.backend.modules.categoria.service.CategoriaService;
import com.power.backend.modules.producto.dto.ProductoRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@org.springframework.test.annotation.DirtiesContext(classMode = org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_CLASS)
public class ProductoIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private CategoriaService categoriaService;

        @Test
        @WithMockUser(username = "admin", roles = "ADMIN")
        void shouldCreateAndRetrieveProducto() throws Exception {
                // 0. Pre-requisite: Create a Category directly via Service (shortcut)
                var catReq = CategoriaRequest.builder().nombre("Deportes").descripcion("Sport").build();
                var catResp = categoriaService.save(catReq);

                // 1. Create Producto
                ProductoRequest prodReq = ProductoRequest.builder()
                                .nombre("Skateboard Pro")
                                .descripcion("Tabla profesional")
                                .precio(new BigDecimal("150.00"))
                                .stock(10)
                                .idCategoria(catResp.id())
                                .activo(true)
                                .destacado(true)
                                .build();

                mockMvc.perform(post("/api/productos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(prodReq)))
                                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.nombre").value("Skateboard Pro"));

                // 2. Get Producto
                mockMvc.perform(get("/api/productos"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$[0].nombre").value("Skateboard Pro"));
        }
}
