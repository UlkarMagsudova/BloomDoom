package com.ltclab.bloomdoomseller.dto.request;
//
//import com.ltclab.bloomdoomseller.dto.request.category.CategoryRequestDtoDemo;

import com.ltclab.bloomdoomseller.dto.request.category.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {
    private PlantRequestDto plantRequestDto;
    private FertilizerRequestDto fertilizerRequestDto;
    private ToolRequestDto toolRequestDto;
    private PotRequestDto potRequestDto;
    private SeedRequestDto seedRequestDto;
//    Double price;
//    Integer stock;
}
