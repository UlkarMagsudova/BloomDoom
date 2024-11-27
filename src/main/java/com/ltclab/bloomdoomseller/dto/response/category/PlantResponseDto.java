package com.ltclab.bloomdoomseller.dto.response.category;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PlantResponseDto {
    private Long id;
    private String subCategory;
    private String type;
    private String description;
//    private MultipartFile image;
    private int humidity;
    private double lightingAngle;
    private double price;
}
