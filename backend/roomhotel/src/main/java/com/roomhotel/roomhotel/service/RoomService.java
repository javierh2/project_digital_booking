package com.roomhotel.roomhotel.service;

import com.roomhotel.roomhotel.dto.CategoryResponseDTO;
import com.roomhotel.roomhotel.dto.RoomRequestDTO;
import com.roomhotel.roomhotel.dto.RoomResponseDTO;
import com.roomhotel.roomhotel.entity.Category;
import com.roomhotel.roomhotel.entity.Room;
import com.roomhotel.roomhotel.exception.DuplicateNameException;
import com.roomhotel.roomhotel.exception.ResourceNotFoundException;
import com.roomhotel.roomhotel.repository.CategoryRepository;
import com.roomhotel.roomhotel.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// le dice a Spring que esta clase es un componente de lógica de negocio
@Service
public class RoomService {

    // inyeccion de dependencias
    private final RoomRepository roomRepository;
    private final CategoryRepository categoryRepository;

    public RoomService(RoomRepository roomRepository, CategoryRepository categoryRepository) {
        this.roomRepository = roomRepository;
        this.categoryRepository = categoryRepository;
    }


    // listar productos
    public List<RoomResponseDTO> getAllRooms() {
        // findAll() trae TODAS las habitaciones de la DB
        // .stream() convierte la lista en un flujo para procesarla
        // .map() convierte cada Room → RoomResponseDTO
        // .collect() junta en una lista nueva
        return roomRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    // obtener habitación por ID, si está vacío, lanzá su excepción
    public RoomResponseDTO getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Habitación no encontrada con id: " + id
                ));

        return convertToResponseDTO(room);
    }


    // listar habitaciones, aleatorias, sin repetir
    public List<RoomResponseDTO> getRandomRooms() {
        // Traemos todas las habitaciones
        List<Room> allRooms = roomRepository.findAll();

        // Collections.shuffle() las mezcla aleatoriamente
        Collections.shuffle(allRooms);

        // máximo 10 y evita errores si hay menos de 10 habitaciones
        return allRooms.subList(0, Math.min(10, allRooms.size()))
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    // registrar habitación
    @Transactional
    // @Transactional ,si funciona impacta en la DB, sino, hace rollback, evita datos incompletos en la DB
    public RoomResponseDTO createRoom(RoomRequestDTO requestDTO) {

        // validacion de nombre único para no repetir
        if (roomRepository.findByName(requestDTO.getName()).isPresent()) {
            throw new DuplicateNameException(
                    "Ya existe una habitación con el nombre: '"
                            + requestDTO.getName() + "'"
            );
        }


        // DTO que llegó del frontend se convierte en una entidad Room para guardarse y persistir en la DB
        Room newRoom = convertToEntity(requestDTO);


        // save() hace el INSERT en la DB
        // nos devuelve la Room ya guardada con el id generado
        Room savedRoom = roomRepository.save(newRoom);


        // Convertimos la entidad guardada en DTO de respuesta
        // para devolvérselo al frontend
        return convertToResponseDTO(savedRoom);
    }


    // elimina un registro de la DB mediante HardDelete
    @Transactional
    public void deleteRoom(Long id) {

        // verificacion de habitación existente
        roomRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Habitación no encontrada con id: " + id
                ));

        // Borra físicamente el registro de la DB
        roomRepository.deleteById(id);
    }


    // Convierte Room (entidad DB) a RoomResponseDTO (lo que ve el frontend)
    // si el room no tiene categoria designada devuelve null en ese campo
    private RoomResponseDTO convertToResponseDTO(Room room) {
        CategoryResponseDTO categoryDTO = null;
        if (room.getCategory() != null){
            categoryDTO = CategoryResponseDTO.builder()
                    .id(room.getCategory().getId())
                    .title(room.getCategory().getTitle())
                    .description(room.getCategory().getDescription())
                    .imageUrl(room.getCategory().getImageUrl())
                    .build();
        }
        return RoomResponseDTO.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .category(categoryDTO)
                .price(room.getPrice())
                .imageRoom(room.getImageRoom())
                .active(room.getActive())
                .build();
    }


    // Convierte RoomRequestDTO (lo que manda el frontend) a Room (entidad DB)
    // si el front manda un categoryId, se buscar la categoria en la DB
    // si no existe, el room se crea sin categoria
    private Room convertToEntity(RoomRequestDTO dto) {
        Category category = null;
        if (dto.getCategoryId() != null){
            category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Categoria no encontrada con id: " + dto.getCategoryId()
                    ));
        }
        return Room.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(category)
                .price(dto.getPrice())
                .imageRoom(dto.getImageRoom())
                .active(true) // toda habitación nueva arranca en "active" (disponible)
                .build();
    }
}