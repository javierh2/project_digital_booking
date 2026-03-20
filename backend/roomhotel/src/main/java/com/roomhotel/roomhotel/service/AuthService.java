package com.roomhotel.roomhotel.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.roomhotel.roomhotel.dto.AuthResponseDTO;
import com.roomhotel.roomhotel.dto.LoginRequestDTO;
import com.roomhotel.roomhotel.dto.RegisterRequestDTO;
import com.roomhotel.roomhotel.entity.Role;
import com.roomhotel.roomhotel.entity.User;
import com.roomhotel.roomhotel.exception.DuplicateNameException;
import com.roomhotel.roomhotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

// servicio de autorización
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailServices;


    // value inyecta de properties el valor seleccionado
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;


    // REGISTRO
    public AuthResponseDTO register(RegisterRequestDTO dto) {

        // verificacion de emails NO duplicados(sería usuario repetido)
        // true = exception http 409
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateNameException("Ya existe un usuario con ese email registrado: " + dto.getEmail());
        }

        // construccion del usuario, contraseña hasheada con .encode() renovada por cada llamada
        User user = User.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);

        //envio de confirmacion luego del guardado de user, si falla el envio no anula el registro
        emailServices.sendRegistrationConfirmation(savedUser.getEmail(), savedUser.getFirstName());

        // jwt generado  y dto cumple el envio del user y el token
        String token = generateToken(savedUser);
        return buildAuthResponse(savedUser,token);

    }

    // LOGIN
    public AuthResponseDTO login (LoginRequestDTO dto){

        //comparacion de password y user(email)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword())
        );

        //autentificacion lograda busca al usuario completo
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado en el sistema"));

        String token = generateToken(user);
        return buildAuthResponse(user,token);
    }

    // jwt firmado con HMAC256 con fecha de creacion-expiración y jwtSecret
    private String generateToken (User user){
        return JWT.create()
                .withSubject(user.getEmail())
                .withClaim("role",user.getRole().name())
                .withClaim("firstName",user.getFirstName())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpiration))
                .sign(Algorithm.HMAC256(jwtSecret));
    }

    // dto de respuesta - usado en login y register
    private AuthResponseDTO buildAuthResponse (User user, String token){
        return AuthResponseDTO.builder()
                .token(token)
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

}
