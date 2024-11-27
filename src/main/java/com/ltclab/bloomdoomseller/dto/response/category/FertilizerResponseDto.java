package com.ltclab.bloomdoomseller.dto.response.category;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FertilizerResponseDto {
    private Long id;
    private String type;
    private String subCategory;
    private String description;
//    private MultipartFile image;
    private String usage;
    private double price;
}
