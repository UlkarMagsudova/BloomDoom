package com.ltclab.bloomdoomseller.controller.category;

import com.ltclab.bloomdoomseller.dto.request.category.FertilizerRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.FertilizerResponseDto;
import com.ltclab.bloomdoomseller.service.category.FertilizerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fertilizer")
@RequiredArgsConstructor
public class FertilizerController {
    private final FertilizerService fertilizerService;

    @PostMapping("/add-fertilizer")
    public ResponseEntity<String> addFertilizer(@Valid @RequestBody FertilizerRequestDto fertilizerRequestDto) {
        try {
            String responseMessage = fertilizerService.addFertilizer(fertilizerRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/readFertilizerById")
    public ResponseEntity<FertilizerResponseDto> readFertilizerById(@RequestParam Long id) {
        try {
            FertilizerResponseDto responseMessage = fertilizerService.readFertilizerById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/read-all-fertilizers")
    public ResponseEntity<List<FertilizerResponseDto>> readAllFertilizers() {
        try {
            List<FertilizerResponseDto> responseMessage = fertilizerService.readAllFertilizers();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateFertilizer(@PathVariable Long id, @Valid @RequestBody FertilizerRequestDto fertilizerRequestDto) {
        try {
            String responseMessage = fertilizerService.updateFertilizer(id, fertilizerRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFertilizer(@PathVariable Long id) {
        try {
            String responseMessage = fertilizerService.deleteFertilizer(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

