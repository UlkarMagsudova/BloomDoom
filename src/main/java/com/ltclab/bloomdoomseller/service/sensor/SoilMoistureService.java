package com.ltclab.bloomdoomseller.service.sensor;

import com.ltclab.bloomdoomseller.dto.request.sensor.SoilMoistureRequestDto;
import com.ltclab.bloomdoomseller.dto.response.sensor.SoilMoistureResponseDto;
import com.ltclab.bloomdoomseller.entity.sensor.SoilMoisture;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.repository.sensor.SoilMoistureRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SoilMoistureService {
    private final SoilMoistureRepository soilMoistureRepository;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;

    public void processSoilMoistureData(SoilMoistureRequestDto soilMoisture) {
        // Here, we just print the data; in a real app, you might save it to a database or perform other logic
        System.out.println("Received Soil Moisture Data: " + soilMoisture.getMoistureLevel());
    }

//    public String saveData(SoilMoistureRequestDto requestDto) {
//        SoilMoisture soilMoisture = modelMapper.map(requestDto, SoilMoisture.class);
//
////        boolean exists = soilMoistureRepository.existsBySensorQR(requestDto.getSensorQR());
////        if (!exists) {
////            throw new NotFoundException("Invalid soil moisture data or QR!");
////        }
//
//        if (requestDto.getMoistureLevel() < 30) {
//            notificationService.sendNotification("Soil moisture is low! Please check your plant.", "user123");
//        }
//
//        soilMoistureRepository.save(soilMoisture);
//
//        return "Soil moisture data has been successfully saved to DB.";
//    }
//
//    public SoilMoistureResponseDto readById(Long id) {
//        SoilMoisture soilMoisture = soilMoistureRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("SoilMoisture not found by id: " + id));
//        return modelMapper.map(soilMoisture, SoilMoistureResponseDto.class);
//    }
//
//    public List<SoilMoistureResponseDto> readAll() {
//        return soilMoistureRepository.findAll().stream()
//                .map(m -> modelMapper.map(m, SoilMoistureResponseDto.class))
//                .toList();
//    }
}
