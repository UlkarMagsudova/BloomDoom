package com.ltclab.bloomdoomseller.dto.request.sensor;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SensorRequestDto {
    private Double humidity;
    private Long customerProductId;
    private String sensorQR;
}
