package com.roomhotel.roomhotel.dto;

import lombok.*;


// lo que el backend devuelve al frontend cuando lista o crea Features
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeatureResponseDTO {
    private Long id;
    private String name;
    private String icon;
}
