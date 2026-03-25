package com.roomhotel.roomhotel.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CategoryRequestDTO {

    @NotBlank(message = "El titulo es obligatorio")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private String imageUrl;

}
