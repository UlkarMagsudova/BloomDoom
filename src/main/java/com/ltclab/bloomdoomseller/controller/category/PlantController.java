package com.ltclab.bloomdoomseller.controller.category;

import com.ltclab.bloomdoomseller.dto.request.category.PlantRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.PlantResponseDto;
import com.ltclab.bloomdoomseller.service.category.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @PostMapping("/add-plant")
    public ResponseEntity<String> addPlant(@Valid @RequestBody PlantRequestDto plantRequestDto) {
        try {
            String responseMessage = plantService.addPlant(plantRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/readPlantById")
    public ResponseEntity<PlantResponseDto> readPlantById(@RequestParam Long id) {
        try {
            PlantResponseDto responseMessage = plantService.readPlantById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/read-all-plants")
    public ResponseEntity<List<PlantResponseDto>> readAllPlants() {
        try {
            List<PlantResponseDto> responseMessage = plantService.readAllPlants();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePlant(@PathVariable Long id, @Valid @RequestBody PlantRequestDto plantRequestDto) {
        try {
            String responseMessage = plantService.updatePlant(id, plantRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePlant(@PathVariable Long id) {
        try {
            String responseMessage = plantService.deletePlant(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

