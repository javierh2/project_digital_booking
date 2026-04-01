package com.roomhotel.roomhotel.controller;


import com.roomhotel.roomhotel.dto.FeatureRequestDTO;
import com.roomhotel.roomhotel.dto.FeatureResponseDTO;
import com.roomhotel.roomhotel.service.FeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/features")
@RequiredArgsConstructor
public class FeatureController {

    private final FeatureService featureService;

    @GetMapping
    public ResponseEntity<List<FeatureResponseDTO>> getAllFeatures(){
        return ResponseEntity.ok(featureService.getAllFeatures());
    }

    @PostMapping
    public ResponseEntity<FeatureResponseDTO> createFeature(
            @Valid @RequestBody FeatureRequestDTO dto){
        FeatureResponseDTO created = featureService.createFeature(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeature(@PathVariable Long id){
        featureService.deleteFeature(id);
        return ResponseEntity.noContent().build();
    }

}