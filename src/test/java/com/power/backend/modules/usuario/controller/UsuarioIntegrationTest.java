package com.power.backend.modules.usuario.controller;

import com.power.backend.security.dto.RegisterRequest;
import com.power.backend.security.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldListUsuariosAsAdmin() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USUARIO")
    void shouldForbidListUsuariosAsNormalUser() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void shouldGetUserById() throws Exception {
        // Create user to fetch
        var register = RegisterRequest.builder()
                .nombre("FetchMe").apellido("Now")
                .email("fetch@test.com").password("pass123").build();
        var user = authService.register(register);

        mockMvc.perform(get("/api/usuarios/" + user.idUsuario()))
                .andExpect(status().isOk());
    }
}
