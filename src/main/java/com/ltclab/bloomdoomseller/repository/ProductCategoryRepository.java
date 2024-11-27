package com.ltclab.bloomdoomseller.repository;

import com.ltclab.bloomdoomseller.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    ProductCategory findByCategory(String category);
    ProductCategory findBySubcategory(String subcategory);
    ProductCategory findByType(String type);

    ProductCategory findByCategoryAndSubcategory(String category, String subcategory);
    ProductCategory findByCategoryAndSubcategoryAndType(String category, String subcategory, String type);
    List<ProductCategory> findAllBySubcategoryIsNotNull();
    List<ProductCategory> findAllByTypeIsNotNull();
}
