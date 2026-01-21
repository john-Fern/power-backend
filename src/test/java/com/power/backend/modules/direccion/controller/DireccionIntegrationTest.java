package com.power.backend.modules.direccion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.backend.modules.direccion.dto.DireccionRequest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DireccionIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private AuthService authService;

        @Test
        @WithMockUser(roles = "ADMIN")
        void shouldCreateAndListDirecciones() throws Exception {
                // 0. Pre-requisite: Ensure a user exists for the FK
                var register = RegisterRequest.builder()
                                .nombre("DirUser").apellido("Test")
                                .email("diruser@test.com").password("pass123").build();
                var authResp = authService.register(register);

                // 1. Create Direccion
                DireccionRequest req = DireccionRequest.builder()
                                .idUsuario(authResp.idUsuario())
                                .calle("Main St")
                                .numero("123")
                                .ciudad("Springfield")
                                .region("State")
                                .pais("USA")
                                .codigoPostal("12345")
                                .esPrincipal(true)
                                .build();

                mockMvc.perform(post("/api/direcciones")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(req)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id").exists())
                                .andExpect(jsonPath("$.calle").value("Main St"));
        }
}
