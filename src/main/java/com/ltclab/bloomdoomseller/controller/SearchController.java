package com.ltclab.bloomdoomseller.controller;

import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.AccountResponseDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.SellerDetailsResponseDto;
import com.ltclab.bloomdoomseller.dto.response.category.*;
import com.ltclab.bloomdoomseller.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/plants")
    public ResponseEntity<Page<PlantResponseDto>> searchPlantsByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<PlantResponseDto> plantPage = searchService.searchPlantsByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(plantPage, HttpStatus.OK);
    }

    @GetMapping("/pots")
    public ResponseEntity<Page<PotResponseDto>> searchPotsByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<PotResponseDto> potPage = searchService.searchPotsByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(potPage, HttpStatus.OK);
    }

    @GetMapping("/fertilizers")
    public ResponseEntity<Page<FertilizerResponseDto>> searchFertilizersByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<FertilizerResponseDto> fertilizerPage = searchService.searchFertilizersByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(fertilizerPage, HttpStatus.OK);
    }

    @GetMapping("/tools")
    public ResponseEntity<Page<ToolResponseDto>> searchToolsByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<ToolResponseDto> toolPage = searchService.searchToolsByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(toolPage, HttpStatus.OK);
    }

    @GetMapping("/seeds")
    public ResponseEntity<Page<SeedResponseDto>> searchSeedsByType(
            @RequestParam("type") String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "type") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<SeedResponseDto> seedPage = searchService.searchSeedsByType(type, page, size, sortField, sortOrder);
        return new ResponseEntity<>(seedPage, HttpStatus.OK);
    }

    @GetMapping("/accounts")
    public ResponseEntity<Page<AccountResponseDto>> searchAccountsByName(
            @RequestParam("name") String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {

        Page<AccountResponseDto> accountPage = searchService.searchAccountsByName(name, page, size, sortField, sortOrder);
        return new ResponseEntity<>(accountPage, HttpStatus.OK);
    }

    @GetMapping("/seller-details")
    public ResponseEntity<Page<SellerDetailsResponseDto>> searchSellerDetailsByShopName(
            @RequestParam("shopName") String shopName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortOrder) {
        Page<SellerDetailsResponseDto> sellerDetailsPage = searchService.searchSellerDetailsByShopName(shopName, page, size, sortField, sortOrder);
        return new ResponseEntity<>(sellerDetailsPage, HttpStatus.OK);
    }

    @GetMapping("/plants/description")
    public ResponseEntity<List<PlantResponseDto>> searchPlants(@RequestParam String keyword) {
        List<PlantResponseDto> plants = searchService.searchPlantsByDescription(keyword);
        return ResponseEntity.ok(plants);
    }

}