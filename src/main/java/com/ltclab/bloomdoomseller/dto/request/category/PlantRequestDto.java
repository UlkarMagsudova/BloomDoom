package com.ltclab.bloomdoomseller.dto.request.category;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class PlantRequestDto {

    @NotNull(message = "SubCategory cannot be null!")
    private String subCategory;
    @NotNull(message = "Type cannot be null!")
    private String type;
    private String description;
    @NotNull(message = "Image of plant cannot be null!")
//    private MultipartFile image;
    private int humidity;
    private double lightingAngle;
    private double price;
    private String sensorQR;
}
