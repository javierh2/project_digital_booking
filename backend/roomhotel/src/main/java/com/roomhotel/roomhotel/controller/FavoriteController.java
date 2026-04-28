package com.roomhotel.roomhotel.controller;

import com.roomhotel.roomhotel.dto.RoomResponseDTO;
import com.roomhotel.roomhotel.service.FavoriteService;
import com.roomhotel.roomhotel.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "http://localhost:5173")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    // @AuthenticationPrincipal inyecta el UserDetails del token JWT
    // Spring Security lo extrae del SecurityContext, no necesitamos leer el token manualmente
    // así el userId viene del token verificado, no de un parámetro manipulable por el cliente

    // GET /api/favorites — lista las rooms favoritas del usuario logueado
    @GetMapping
    public ResponseEntity<List<RoomResponseDTO>> getFavorites(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        return ResponseEntity.ok(favoriteService.getFavoriteRooms(userId));
    }

    // GET /api/favorites/ids — solo los roomIds marcados como favoritos
    // más eficiente que traer los objetos completos cuando solo necesitamos
    // saber qué corazones pintar activos en el catálogo
    @GetMapping("/ids")
    public ResponseEntity<List<Long>> getFavoriteIds(
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        return ResponseEntity.ok(favoriteService.getFavoriteRoomIds(userId));
    }

    // POST /api/favorites/{roomId} — agrega a favoritos
    @PostMapping("/{roomId}")
    public ResponseEntity<Void> addFavorite(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        favoriteService.addFavorite(userId, roomId);
        return ResponseEntity.ok().build();
    }

    // DELETE /api/favorites/{roomId} — quita de favoritos
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> removeFavorite(
            @PathVariable Long roomId,
            @AuthenticationPrincipal UserDetails userDetails) {
        Long userId = userService.getUserIdByEmail(userDetails.getUsername());
        favoriteService.removeFavorite(userId, roomId);
        return ResponseEntity.noContent().build();
    }

}