package com.ltclab.bloomdoomseller.dto.request.category;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter
@Setter
public class SeedRequestDto {
    @NotNull(message = "SubCategory cannot be null!")
    private String subCategory;
    @NotNull(message = "Type cannot be null!")
    private String type;
    private String description;
    private String plantingSeason;
    private String sunlightRequirement;
    private String wateringRequirement;
    private String soilType;
    private BigDecimal price;
    private Integer quantity;
    @NotNull(message = "Image of seed cannot be null!")
    private MultipartFile image;
    private Double rating;
}
