package com.power.backend.security.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.power.backend.security.dto.AuthRequest;
import com.power.backend.security.dto.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test") // Ensures H2 is used
public class AuthIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Test
        void shouldRegisterAndLoginSuccessfully() throws Exception {
                // 1. Register
                RegisterRequest registerRequest = RegisterRequest.builder()
                                .nombre("TestUser")
                                .apellido("TestLast")
                                .email("test@example.com")
                                .password("password123")
                                .build();

                mockMvc.perform(post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.token").exists());

                // 2. Login
                AuthRequest authRequest = AuthRequest.builder()
                                .email("test@example.com")
                                .password("password123")
                                .build();

                mockMvc.perform(post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.token").exists());
        }

        @Test
        void shouldFailLoginWithWrongPassword() throws Exception {
                // 1. Register (needed because H2 is fresh)
                RegisterRequest registerRequest = RegisterRequest.builder()
                                .nombre("WrongPassUser")
                                .apellido("TestLast")
                                .email("wrongpass@example.com")
                                .password("password123")
                                .build();

                mockMvc.perform(post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest)))
                                .andExpect(status().isCreated());

                // 2. Try Login with wrong password
                AuthRequest authRequest = AuthRequest.builder()
                                .email("wrongpass@example.com")
                                .password("wrongpassword")
                                .build();

                mockMvc.perform(post("/auth/login") // Usually returns 401 or 403 depending on config
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authRequest)))
                                .andDo(org.springframework.test.web.servlet.result.MockMvcResultHandlers.print())
                                .andExpect(status().is4xxClientError());
        }
}
