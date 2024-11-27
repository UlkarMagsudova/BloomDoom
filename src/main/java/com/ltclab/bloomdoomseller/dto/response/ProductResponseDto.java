package com.ltclab.bloomdoomseller.dto.response;

import com.ltclab.bloomdoomseller.dto.request.category.FertilizerRequestDto;
import com.ltclab.bloomdoomseller.dto.request.category.PlantRequestDto;
import com.ltclab.bloomdoomseller.dto.request.category.PotRequestDto;
import com.ltclab.bloomdoomseller.dto.request.category.ToolRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.SeedResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDto {
    private PlantRequestDto plantRequestDto;
    private FertilizerRequestDto fertilizerRequestDto;
    private ToolRequestDto toolRequestDto;
    private PotRequestDto potRequestDto;
    private SeedResponseDto seedResponseDto;
}
