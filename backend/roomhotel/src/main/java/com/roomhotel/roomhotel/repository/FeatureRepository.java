package com.roomhotel.roomhotel.repository;

import com.roomhotel.roomhotel.entity.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


// existsByName para validar duplicados antes de guardar, el resto brinda JPA
@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {

    Optional<Feature> findByName(String name);
}
