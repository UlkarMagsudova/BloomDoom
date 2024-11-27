package com.ltclab.bloomdoomseller.controller.sensor;

import com.ltclab.bloomdoomseller.dto.request.sensor.SensorRequestDto;
import com.ltclab.bloomdoomseller.dto.response.sensor.SensorResponseDto;
import com.ltclab.bloomdoomseller.service.sensor.SensorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
@RequiredArgsConstructor
public class SensorController {
    private final SensorService sensorService;

    @PostMapping("/save-data")
    public ResponseEntity<String> saveData(@RequestParam String sensorQr, @Valid @RequestBody SensorRequestDto sensorRequestDto) {
        String responseMessage = sensorService.saveData(sensorQr, sensorRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMessage);
    }

    @GetMapping("/readSensorById")
    public ResponseEntity<SensorResponseDto> readSensorById(@RequestParam Long id) {
        SensorResponseDto responseMessage = sensorService.readById(id);
        return ResponseEntity.ok(responseMessage);
    }

    @GetMapping("/read-all-sensors")
    public ResponseEntity<List<SensorResponseDto>> readAll() {
        return ResponseEntity.ok(sensorService.readAll());
    }

}
