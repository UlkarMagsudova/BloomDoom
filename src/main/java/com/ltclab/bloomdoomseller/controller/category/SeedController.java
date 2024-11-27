package com.ltclab.bloomdoomseller.controller.category;

import com.ltclab.bloomdoomseller.dto.request.category.SeedRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.SeedResponseDto;
import com.ltclab.bloomdoomseller.service.category.SeedService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seed")
@RequiredArgsConstructor
public class SeedController {
    private final SeedService seedService;

    @PostMapping("/add-seed")
    public ResponseEntity<String> addSeed(@Valid @RequestBody SeedRequestDto seedRequestDto) {
        try {
            String responseMessage = seedService.addSeed(seedRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/readSeedById")
    public ResponseEntity<SeedResponseDto> readSeedById(@RequestParam Long id) {
        try {
            SeedResponseDto responseMessage = seedService.readSeedById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/read-all-seeds")
    public ResponseEntity<List<SeedResponseDto>> readAllSeeds() {
        try {
            List<SeedResponseDto> responseMessage = seedService.readAllSeeds();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSeed(@PathVariable Long id, @Valid @RequestBody SeedRequestDto seedRequestDto) {
        try {
            String responseMessage = seedService.updateSeed(id, seedRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSeed(@PathVariable Long id) {
        try {
            String responseMessage = seedService.deleteSeed(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
