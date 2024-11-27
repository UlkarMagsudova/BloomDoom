package com.ltclab.bloomdoomseller.dto.response.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToolResponseDto {
    private Long id;
    private String subCategory;
    private String type;
    private String description;
    //    private MultipartFile image;
    private String function;
    private double price;
}
