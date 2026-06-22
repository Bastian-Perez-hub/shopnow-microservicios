package com.example.moduloPedidos.controller;

import com.example.moduloPedidos.dto.AuthResponse;
import com.example.moduloPedidos.dto.LoginRequest;
import com.example.moduloPedidos.model.User;
import com.example.moduloPedidos.security.JwtService;
import com.example.moduloPedidos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticación", description = "Operaciones de login y generación de token JWT")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    @Operation(
        summary = "Iniciar sesión",
        description = "Recibe usuario y contraseña, valida las credenciales y retorna un token JWT para usar en los demás endpoints"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso, retorna el token JWT"),
        @ApiResponse(responseCode = "500", description = "Credenciales inválidas o usuario no encontrado")
    })
    public AuthResponse login(@RequestBody LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Credenciales invalidas");
        }

        String token = jwtService.generateToken(user.getUsername(), user.getRole().name());
        return new AuthResponse(token);
    }
}
