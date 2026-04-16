package com.roomhotel.roomhotel.service;

import com.roomhotel.roomhotel.dto.BookingResponseDTO;
import com.roomhotel.roomhotel.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    // devuelve todos los rangos de fechas ocupadas para una room específica
    // el frontend usa estos rangos para pintar los días en el calendario
    // no devuelve días individuales, sería ineficiente para reservas largas
    // el frontend calcula qué días caen dentro de cada rango
    public List<BookingResponseDTO> getOccupiedDatesByRoom(Long roomId) {
        return bookingRepository.findByRoomId(roomId)
                .stream()
                .map(booking -> BookingResponseDTO.builder()
                        .id(booking.getId())
                        .roomId(booking.getRoom().getId())
                        .checkIn(booking.getCheckIn())
                        .checkOut(booking.getCheckOut())
                        .build())
                .collect(Collectors.toList());
    }
}