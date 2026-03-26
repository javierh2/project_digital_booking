package com.roomhotel.roomhotel.service;

import com.roomhotel.roomhotel.dto.CategoryRequestDTO;
import com.roomhotel.roomhotel.dto.CategoryResponseDTO;
import com.roomhotel.roomhotel.entity.Category;
import com.roomhotel.roomhotel.exception.DuplicateNameException;
import com.roomhotel.roomhotel.exception.ResourceNotFoundException;
import com.roomhotel.roomhotel.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;

    // devuelve todas las categorias, en el home y en el formulario de creación de "rooms"
    public List<CategoryResponseDTO> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // devuelve una categoria por id
    public CategoryResponseDTO getCategoryById(Long id){
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con id: " + id));
        return convertToResponseDTO(category);
    }

    // crear nueva categoria, solo "ADMIN"
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {
        if (categoryRepository.existsByTitle(dto.getTitle())) {
            throw new DuplicateNameException("Ya existe una categoria con el título: " + dto.getTitle());
        }

        Category category = Category.builder()
                .title(dto.getTitle())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .build();
        return convertToResponseDTO(categoryRepository.save(category));
    }

    // eliminar una categoria, solo "ADMIN"
    public void deleteCategoryById(Long id){
        if(!categoryRepository.existsById(id)){
            throw new ResourceNotFoundException("Categoria no encontrada con id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // convierte entidad a DTO
    private CategoryResponseDTO convertToResponseDTO(Category category){
        return CategoryResponseDTO.builder()
                .id(category.getId())
                .title(category.getTitle())
                .description(category.getDescription())
                .imageUrl(category.getImageUrl())
                .build();
    }

}
