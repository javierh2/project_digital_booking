package com.roomhotel.roomhotel.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que el backend devuelve en el registro y login
// el frontend guarda el token en localStorage y lo manda en cada request
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
