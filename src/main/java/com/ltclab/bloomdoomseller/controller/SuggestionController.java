package com.ltclab.bloomdoomseller.controller;

import com.ltclab.bloomdoomseller.dto.request.SuggestionRequestDto;
import com.ltclab.bloomdoomseller.dto.response.SuggestionResponseDto;
import com.ltclab.bloomdoomseller.service.SuggestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(("/suggestions"))
@RequiredArgsConstructor
public class SuggestionController {
    private final SuggestionService suggestionService;

    @PostMapping("/create")
    public ResponseEntity<String> createSuggestion(@Valid @RequestBody SuggestionRequestDto suggestionRequestDto) {
        suggestionService.createSuggestion(suggestionRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Suggestion created successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuggestionResponseDto> getSuggestionById(@PathVariable Long id) {
        SuggestionResponseDto suggestionResponseDto = suggestionService.getSuggestionById(id);
        return ResponseEntity.ok(suggestionResponseDto);
    }

    @GetMapping("/get/by{creatorid}")
    public ResponseEntity<List<SuggestionResponseDto>> getSuggestionsByCreatorId(@RequestParam Long creatorId) {
        return ResponseEntity.ok(suggestionService.getSuggestionsByCreatorId(creatorId));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<SuggestionResponseDto>> getAllSuggestions() {
        return ResponseEntity.ok(suggestionService.getAllSuggestions());
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateSuggestion(@RequestParam Long id, @Valid @RequestBody SuggestionRequestDto suggestionRequestDto) {
        return ResponseEntity.ok(suggestionService.updateSuggestion(id, suggestionRequestDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSuggestionById(@RequestParam Long id, @RequestParam Long currentUserId) {
        return ResponseEntity.ok(suggestionService.deleteSuggestionById(id, currentUserId));
    }

    @GetMapping("/search{type}")
    public ResponseEntity<Page<SuggestionResponseDto>> searchSuggestionsByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<SuggestionResponseDto> suggestionPage = suggestionService.searchSuggestionsByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(suggestionPage, HttpStatus.OK);
    }

    @GetMapping("/search/by{content}")
    public ResponseEntity<List<SuggestionResponseDto>> searchSuggestions(@RequestParam String keyword) {
        List<SuggestionResponseDto> suggestions = suggestionService.searchSuggestionsByContent(keyword);
        return ResponseEntity.ok(suggestions);
    }
}