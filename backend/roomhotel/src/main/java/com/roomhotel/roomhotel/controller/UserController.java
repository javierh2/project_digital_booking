package com.roomhotel.roomhotel.controller;

import com.roomhotel.roomhotel.dto.RoleUpdateRequestDTO;
import com.roomhotel.roomhotel.dto.UserResponseDTO;
import com.roomhotel.roomhotel.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // GET /api/users — solo ROLE_ADMIN
    // devuelve la lista completa de usuarios registrados
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // PUT /api/users/{id}/role — solo ROLE_ADMIN
    // recibe el nuevo rol en el body y lo aplica al usuario con ese id
    // PUT para reemplazar un campo específico del recurso User
    @PutMapping("/{id}/role")
    public ResponseEntity<UserResponseDTO> updateRole(
            @PathVariable Long id,
            @Valid @RequestBody RoleUpdateRequestDTO request
    ) {
        UserResponseDTO updated = userService.updateRole(id, request.getRole());
        return ResponseEntity.ok(updated);
    }
}