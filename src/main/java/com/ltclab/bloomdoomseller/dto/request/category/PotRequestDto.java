package com.ltclab.bloomdoomseller.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class PotRequestDto {
    @NotNull(message = "SubCategory cannot be null!")
    private String subCategory;
    @NotNull(message = "Type cannot be null!")
    private String type;
    private String description;
    @NotNull(message = "Image of pot cannot be null!")
    private MultipartFile image;
    private String size;
    private double price;
}
