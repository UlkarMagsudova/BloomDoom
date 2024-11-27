package com.ltclab.bloomdoomseller.service.category;


import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.category.FertilizerRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.FertilizerResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.category.Fertilizer;
import com.ltclab.bloomdoomseller.exception.*;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.category.FertilizerRepository;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FertilizerService {
    private final FertilizerRepository fertilizerRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String addFertilizer(FertilizerRequestDto fertilizerRequestDto) {
        Fertilizer mapped = modelMapper.map(fertilizerRequestDto, Fertilizer.class);

        Category category = Category.FERTILIZER;

        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && subcat
                        .getSubcategory()
                        .equalsIgnoreCase(fertilizerRequestDto.getSubCategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }

        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && type
                        .getSubcategory()
                        .equalsIgnoreCase(fertilizerRequestDto.getSubCategory()) && type
                        .getType()
                        .equalsIgnoreCase(fertilizerRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidTypeException("Invalid type for the given subcategory");
        }

        boolean check = fertilizerRepository.existsBySubCategory(fertilizerRequestDto.getType());
        if (check) {
            log.info("Fertilizer already exists with type {}", fertilizerRequestDto.getSubCategory());
            throw new AlreadyExistsException("Fertilizer already exists");
        }
        log.info("Adding fertilizer with type {}", fertilizerRequestDto.getType());
        fertilizerRepository.save(mapped);
        return "Fertilizer type has successfully been added to DB.";

    }

    public FertilizerResponseDto readFertilizerById(Long id) {
        Fertilizer fertilizer = fertilizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fertilizer with id " + id + " does not exist."));

        log.info("Find fertilizer by id {}", id);
        return modelMapper.map(fertilizer, FertilizerResponseDto.class);
    }

    public List<FertilizerResponseDto> readAllFertilizers() {
        log.info("Finding all fertilizers from DB.");
        return fertilizerRepository.
                findAll()
                .stream()
                .map(m -> modelMapper.map(m, FertilizerResponseDto.class))
                .toList();
    }

    public String updateFertilizer(Long id, FertilizerRequestDto fertilizerRequestDto) {
        Fertilizer fertilizer = fertilizerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fertilizer with id " + id + " does not exist."));

        if(!fertilizer.getSubCategory().equals(fertilizerRequestDto.getSubCategory())) {
            if(fertilizerRepository.existsBySubCategory(fertilizerRequestDto.getSubCategory())) {
                throw new UniqueFieldException("Fertilizer subcategory already exists.");
            }
        }

        modelMapper.map(fertilizerRequestDto, fertilizer);
        fertilizerRepository.save(fertilizer);
        log.info("Fertilizer with type {} has been updated.", fertilizerRequestDto.getType());
        return "Fertilizer with id " + id + " has successfully updated.";
    }

    public String deleteFertilizer(Long id) {
        Fertilizer fertilizer = fertilizerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fertilizer with id " + id + " does not exist."));

        fertilizerRepository.delete(fertilizer);
        log.info("Fertilizer with id {} has been deleted.", id);
        return "Fertilizer with id " + id + " has been deleted.";
    }
}