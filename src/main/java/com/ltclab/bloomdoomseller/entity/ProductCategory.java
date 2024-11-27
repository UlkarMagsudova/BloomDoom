package com.ltclab.bloomdoomseller.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "product_categories")
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String category;
    @Column(unique = true, nullable = false)
    private String subcategory;
    @Column(unique = true)
    private String type;

    public ProductCategory() {
    }

    public ProductCategory(String category, String subcategory, String type) {
        this.category = category;
        this.subcategory = subcategory;
        this.type = type;
    }

}