package com.ltclab.bloomdoomseller.controller;

import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @PostMapping("/add")
    public ResponseEntity<String> addType(@RequestParam Category category,
                                          @RequestParam String subcategory,
                                          @RequestParam String type) {
        return ResponseEntity.ok(productCategoryService.addType(category, subcategory, type));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        List<ProductCategory> categories = productCategoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/subcategories")
    public ResponseEntity<List<ProductCategory>> getAllSubcategories() {
        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        return ResponseEntity.ok(subcategories);
    }

    @GetMapping("/types")
    public ResponseEntity<List<ProductCategory>> getAllTypes() {
        List<ProductCategory> types = productCategoryService.getAllTypes();
        return ResponseEntity.ok(types);
    }

    @DeleteMapping("/subcategories/{id}")
    public ResponseEntity<Void> deleteSubcategoryEditor(@PathVariable Long id) {
        productCategoryService.deleteSubcategoryEditor(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/types/{id}")
    public ResponseEntity<Void> deleteTypeEditor(@PathVariable Long id) {
        productCategoryService.deleteTypeEditor(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @PostMapping("/add-subcategpry/editor")
    public ResponseEntity<String> addSubcategoryEditor(@PathVariable Category category,
                                                       @PathVariable String subcategory) {
        return ResponseEntity.ok(productCategoryService.addSubcategoryEditor(category, subcategory));
    }

    @PostMapping("/add-type/editor")
    public ResponseEntity<String> addTypeEditor(@PathVariable Category category,
                                                @PathVariable String subcategory,
                                                @PathVariable String type) {
        return ResponseEntity.ok(productCategoryService.addTypeEditor(category, subcategory, type));
    }

}