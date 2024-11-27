package com.ltclab.bloomdoomseller.service.sensor;

import com.ltclab.bloomdoomseller.dto.request.sensor.SensorRequestDto;
import com.ltclab.bloomdoomseller.dto.response.sensor.SensorResponseDto;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import com.ltclab.bloomdoomseller.repository.sensor.SensorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SensorService{
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    public String saveData(String sensorQr, SensorRequestDto sensorRequestDto) {
        Sensor mapped = modelMapper.map(sensorRequestDto, Sensor.class);

        boolean exists = sensorRepository.existsBySensorQR(sensorRequestDto.getSensorQR());
        if (!exists || !sensorRequestDto.getSensorQR().equalsIgnoreCase(sensorQr) || sensorRequestDto.getHumidity() <= 0) {
            throw new NotFoundException("Invalid sensor data or QR!");
        }

        sensorRepository.save(mapped);
        log.info("Saved sensor: {}", mapped);
        return "Sensor data has been successfully saved to DB.";
    }

    public SensorResponseDto readById(Long id) {
        Sensor sensor = sensorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Sensor not found by id: " + id));
        return modelMapper.map(sensor, SensorResponseDto.class);
    }

    public List<SensorResponseDto> readAll() {
        return sensorRepository.findAll().stream()
                .map(sensor -> modelMapper.map(sensor, SensorResponseDto.class))
                .toList();
    }
}

