package com.ltclab.bloomdoomseller.dto.response.category;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SeedResponseDto {
    private Long id;
    private String subCategory;
    private String productType;
    private String description;
    private String plantingSeason;
    private String sunlightRequirement;
    private String wateringRequirement;
    private String soilType;
    private BigDecimal price;
    private Integer quantity;
//    private MultipartFile image;
    private Double rating;
}
