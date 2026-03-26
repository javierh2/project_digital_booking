package com.roomhotel.roomhotel.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// lo que devuelve el backend al frontend
// incluyendo el id para que el front pueda referenciar la categoría
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String imageUrl;
}
