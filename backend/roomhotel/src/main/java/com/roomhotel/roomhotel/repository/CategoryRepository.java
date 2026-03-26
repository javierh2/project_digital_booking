package com.roomhotel.roomhotel.repository;

import com.roomhotel.roomhotel.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// Repositorio para la entidad Category, con método personalizado para detectar títulos duplicados antes de crear
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByTitle(String title);
    boolean existsByTitle(String title);
}
