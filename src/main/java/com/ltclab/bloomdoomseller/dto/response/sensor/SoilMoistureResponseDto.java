package com.ltclab.bloomdoomseller.dto.response.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SoilMoistureResponseDto {
    private Long id;
    private Integer moistureLevel;
    private Long customerProductId;
    private String sensorQR;
}
