package com.ltclab.bloomdoomseller.service;

import com.ltclab.bloomdoomseller.client.GeminiClient;
import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.TypeValidationException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;
    private final GeminiClient geminiClient;
    private final String apiKey = "AIzaSyC1EoT1o0Lk2nwrHn4yh_F_0sqXsGJOlts";

    @Transactional
    public String addType(Category category, String subcategory, String type) {

        String formattedSubcategory = capitalizeWords(subcategory);
        String formattedType = capitalizeWords(type);

        // Validate the type using AI Validator (GeminiClient)
        try {
            boolean isValid = geminiClient
                    .validateType(category.toString(), subcategory, type, apiKey);
            if (!isValid) {
                throw new IllegalArgumentException("Type '" + type +
                        "' is not valid for subcategory '" + subcategory + "' in category '" + category + "'.");
            }
        } catch (IOException e) {
            throw new TypeValidationException("Failed to validate type: " + e.getMessage());
        }
        // Create and save the new product category
        ProductCategory newType = new ProductCategory(category.toString(), formattedSubcategory, formattedType);
        productCategoryRepository.save(newType);
        return "The type has been added successfully.";
    }

    private String capitalizeWords(String input) {
        return Arrays.stream(input.split("\\s+"))
                .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));
    }

    public List<ProductCategory> getAllCategories() {
        return productCategoryRepository.findAll();
    }

    public List<ProductCategory> getAllSubcategories() {
        return productCategoryRepository.findAllBySubcategoryIsNotNull();
    }

    public List<ProductCategory> getAllTypes() {
        return productCategoryRepository.findAllByTypeIsNotNull();
    }

    public void deleteSubcategoryEditor(Long id) {
        if (!productCategoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subcategory with ID " + id + " not found.");
        }
        productCategoryRepository.deleteById(id);
    }

    public void deleteTypeEditor(Long id) {
        if (!productCategoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Type with ID " + id + " not found.");
        }
        productCategoryRepository.deleteById(id);
    }

    public String addSubcategoryEditor(Category category, String subcategory) {
        String formattedSubcategory = capitalizeWords(subcategory);
        boolean subcategoryExists = productCategoryRepository.
                findByCategoryAndSubcategory(category.toString(), formattedSubcategory) != null;
        if (subcategoryExists) {
            throw new AlreadyExistsException("Subcategory already exists for the given category");
        }
        ProductCategory newSubcategory = new ProductCategory(category.toString(), formattedSubcategory, null);
        productCategoryRepository.save(newSubcategory);
        return "The subcategory has been added successfully.";
    }
    @Transactional
    public String addTypeEditor(Category category, String subcategory, String type) {
        String formattedType = capitalizeWords(type);

        boolean typeExists = productCategoryRepository
                .findByCategoryAndSubcategoryAndType(category.toString(), subcategory, formattedType) != null;
        if (typeExists) {
            throw new AlreadyExistsException("Type already exists for the given category and subcategory");
        }
        ProductCategory newType = new ProductCategory(category.toString(), subcategory, formattedType);
        productCategoryRepository.save(newType);
        return "The type has been added successfully.";
    }

}
