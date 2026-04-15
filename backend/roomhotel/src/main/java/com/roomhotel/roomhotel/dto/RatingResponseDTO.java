package com.roomhotel.roomhotel.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingResponseDTO {

    private Long id;
    // nombre del usuario que hizo la reseña — criterio de aceptación explícito
    private String userName;
    private Integer stars;
    // comentario puede ser null — el frontend lo maneja con una guarda
    private String comment;
    // fecha de publicación — criterio de aceptación explícito
    private LocalDateTime createdAt;
}