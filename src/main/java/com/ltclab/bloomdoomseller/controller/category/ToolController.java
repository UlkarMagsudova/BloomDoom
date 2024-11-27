package com.ltclab.bloomdoomseller.controller.category;

import com.ltclab.bloomdoomseller.dto.request.category.ToolRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.ToolResponseDto;
import com.ltclab.bloomdoomseller.service.category.ToolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tool")
@RequiredArgsConstructor
public class ToolController {
    private final ToolService toolService;

    @PostMapping("/add-tool")
    public ResponseEntity<String> addTool(@Valid @RequestBody ToolRequestDto toolRequestDto) {
        try {
            String responseMessage = toolService.addTool(toolRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/readToolById")
    public ResponseEntity<ToolResponseDto> readToolById(@RequestParam Long id) {
        try {
            ToolResponseDto responseMessage = toolService.readToolById(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/read-all-tools")
    public ResponseEntity<List<ToolResponseDto>> readAllTools() {
        try {
            List<ToolResponseDto> responseMessage = toolService.readAllTools();
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateTool(@PathVariable Long id, @Valid @RequestBody ToolRequestDto toolRequestDto) {
        try {
            String responseMessage = toolService.updateTool(id, toolRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTool(@PathVariable Long id) {
        try {
            String responseMessage = toolService.deleteTool(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}

