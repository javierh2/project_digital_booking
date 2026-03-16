package com.roomhotel.roomhotel.controller;

import com.roomhotel.roomhotel.dto.AuthResponseDTO;
import com.roomhotel.roomhotel.dto.LoginRequestDTO;
import com.roomhotel.roomhotel.dto.RegisterRequestDTO;
import com.roomhotel.roomhotel.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
//endpoints de auth "/api/auth", publicos y definidos en SecurityConfig
public class AuthController {

    //inyeccion de dependencias
    private final AuthService authService;

    //registro de usuario, recibe un RegisterRequestDTO con los datos del usuario, valida y devuelve un AuthResponseDTO con el token JWT
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request
            ){
        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    //login de usuario, recibe un LoginRequestDTO con el email y password, valida y devuelve un AuthResponseDTO con el token JWT
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request
    ){
        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
