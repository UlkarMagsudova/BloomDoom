package com.ltclab.bloomdoomseller.controller.sensor;

import com.ltclab.bloomdoomseller.dto.request.sensor.SoilMoistureRequestDto;
import com.ltclab.bloomdoomseller.dto.response.sensor.SoilMoistureResponseDto;
import com.ltclab.bloomdoomseller.entity.sensor.SoilMoisture;
import com.ltclab.bloomdoomseller.service.sensor.SoilMoistureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SoilMoistureController {
    private final SoilMoistureService soilMoistureService;

    @PostMapping("/soilMoisture")
    public String receiveSoilMoistureData(@RequestBody SoilMoistureRequestDto soilMoisture) {
        // Pass the received data to the service
        soilMoistureService.processSoilMoistureData(soilMoisture);

        // Return a response message
        return ("Soil moisture data received: " + soilMoisture.getMoistureLevel());
    }
}
