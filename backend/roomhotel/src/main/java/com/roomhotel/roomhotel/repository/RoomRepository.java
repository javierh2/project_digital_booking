package com.roomhotel.roomhotel.repository;

import com.roomhotel.roomhotel.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Repositorio para la entidad Room, con métodos personalizados para buscar por nombre
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByName(String name);
}