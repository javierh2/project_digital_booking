package com.roomhotel.roomhotel.repository;

import com.roomhotel.roomhotel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

//Repositorio para la entidad User, con métodos personalizados para buscar por email y verificar existencia
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
