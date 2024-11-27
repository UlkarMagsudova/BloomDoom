package com.ltclab.bloomdoomseller.dto.response.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorResponseDto {
    private Long id;
    private Double humidity;
    private Double lighting;
    private Long appId;
    private String sensorQR;
}
