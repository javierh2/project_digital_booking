package com.roomhotel.roomhotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// lo que recibe el backend cuando el ADMIN crea o edita una Feature
// solo necesita name e icon, el id lo genera la DB
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FeatureRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String icon;
}
