package com.ltclab.bloomdoomseller.controller;

import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.ProductRequestDto;
import com.ltclab.bloomdoomseller.dto.response.ProductResponseDto;
import com.ltclab.bloomdoomseller.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create/{category}")
    public ResponseEntity<String> createProduct(@RequestParam(required = false) Category category,
                                                @Valid @RequestBody ProductRequestDto productRequestDto) {
        try {
            String responseMessage = productService.createProduct(category, productRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/all-products/{category}")
    public ResponseEntity<List<?>> getProductsByCategory(@RequestParam Category category) {
        List<?> products = productService.getProductsByCategory(category);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/all-products-category")
    public ResponseEntity<List<?>> getAllProducts() {
        List<?> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{category}/{productId}")
    public ResponseEntity<Object> getProductById(@PathVariable Category category, @RequestParam Long productId) {
        return ResponseEntity.ok(productService.getProductById(category, productId));
    }

    @PutMapping("/update/{category}/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Category category,
                                                @PathVariable Long productId,
                                                @Valid @RequestBody ProductRequestDto productRequestDto) {
        return ResponseEntity.ok(productService.updateProduct(category, productId, productRequestDto));
    }

    @DeleteMapping("/{category}/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Category category, @PathVariable Long productId) {
        productService.deleteProduct(category, productId);
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/get/by-account/{accountId}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByAccount(@PathVariable Long accountId) {
        List<ProductResponseDto> products = productService.getProductsByAccount(accountId);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/by{shopName}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByShopName(@RequestParam String shopName) {
        List<ProductResponseDto> products = productService.getProductsByShopName(shopName);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/by{city}")
    public ResponseEntity<List<ProductResponseDto>> getProductsByCity(@RequestParam String city) {
        List<ProductResponseDto> products = productService.getProductsByCity(city);
        return ResponseEntity.ok(products);
    }
}