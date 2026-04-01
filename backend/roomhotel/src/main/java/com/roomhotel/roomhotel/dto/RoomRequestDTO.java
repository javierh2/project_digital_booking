package com.roomhotel.roomhotel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

/*
DTO  viaja entre frontend y backend
   → solo los campos necesarios
   → uno para RECIBIR datos (Request)
   → uno para ENVIAR datos (Response)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomRequestDTO {

    // campos del frontend
    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private Long categoryId;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    private Double price;

    private String imageRoom;

    private List<Long> featureIds;
}