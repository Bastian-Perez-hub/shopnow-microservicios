package com.example.shopNow.service;

import com.example.moduloPedidos.controller.AuthController;
import com.example.moduloPedidos.dto.AuthResponse;
import com.example.moduloPedidos.dto.LoginRequest;
import com.example.moduloPedidos.model.Role;
import com.example.moduloPedidos.model.User;
import com.example.moduloPedidos.security.JwtService;
import com.example.moduloPedidos.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthController authController;

    @Test
    void login_conCredencialesValidas_debeRetornarTokenFalso() {
        LoginRequest request = new LoginRequest();
        request.setUsername("juan");
        request.setPassword("1234");

        User user = new User(1L, "juan", "hashedPassword", Role.ROLE_USER);

        when(userService.findByUsername("juan")).thenReturn(user);
        when(passwordEncoder.matches("1234", "hashedPassword")).thenReturn(true);
        when(jwtService.generateToken("juan", "ROLE_USER")).thenReturn("token-falso");

        AuthResponse response = authController.login(request);

        assertEquals("token-falso", response.getToken());
        System.out.println("Test login ejecutado con exito - token generado: " + response.getToken());
    }
}
