package com.roomhotel.roomhotel.controller;

import com.roomhotel.roomhotel.dto.BookingResponseDTO;
import com.roomhotel.roomhotel.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "http://localhost:5173")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // GET /api/bookings/room/{roomId}/occupied-dates
    // público, cualquier visitante necesita ver disponibilidad sin estar logueado
    // devuelve lista de rangos {checkIn, checkOut} para que el frontend calcule qué días individuales marcar como ocupados en el calendario
    @GetMapping("/room/{roomId}/occupied-dates")
    public ResponseEntity<List<BookingResponseDTO>> getOccupiedDates(
            @PathVariable Long roomId) {
        return ResponseEntity.ok(bookingService.getOccupiedDatesByRoom(roomId));
    }
}