package com.roomhotel.roomhotel.dto;

import com.roomhotel.roomhotel.entity.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// body del PUT /api/users/{id}/role
// el frontend manda el nuevo rol que quiere asignar
// separamos esto en un DTO en lugar de leer el rol como @PathVariable por posibles nuevos campos
@Getter
@Setter
@NoArgsConstructor
public class RoleUpdateRequestDTO {

    @NotNull(message = "El rol es obligatorio")
    private Role role;
}