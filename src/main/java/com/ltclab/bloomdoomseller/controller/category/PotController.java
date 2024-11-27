package com.ltclab.bloomdoomseller.controller.category;

import com.ltclab.bloomdoomseller.dto.request.category.PotRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.PotResponseDto;
import com.ltclab.bloomdoomseller.service.category.PotService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pot")
@RequiredArgsConstructor
public class PotController {
    private final PotService potService;

    @PostMapping("/add-pot")
    public ResponseEntity<String> addPot(@Valid @RequestBody PotRequestDto potRequestDto) {
        try {
            String responseMessage = potService.addPot(potRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/readPotById")
    public ResponseEntity<PotResponseDto> readPotById(@RequestParam Long id) {
        try {
            PotResponseDto responseMessage = potService.readPotById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/read-all-pots")
    public ResponseEntity<List<PotResponseDto>> readAllPots() {
        try {
            List<PotResponseDto> responseMessage = potService.readAllPots();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updatePot(@PathVariable Long id, @Valid @RequestBody PotRequestDto potRequestDto) {
        try {
            String responseMessage = potService.updatePot(id, potRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePot(@PathVariable Long id) {
        try {
            String responseMessage = potService.deletePot(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
