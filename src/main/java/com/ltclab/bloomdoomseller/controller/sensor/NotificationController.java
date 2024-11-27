package com.ltclab.bloomdoomseller.controller.sensor;

import com.ltclab.bloomdoomseller.dto.request.sensor.SoilMoistureRequestDto;
import com.ltclab.bloomdoomseller.entity.category.Plant;
import com.ltclab.bloomdoomseller.entity.sensor.SoilMoisture;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.repository.category.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//
//import com.ltclab.bloomdoomseller.entity.account.Account;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
// SensorController.java
@RestController
@RequestMapping("/api/sensor")
@RequiredArgsConstructor
public class NotificationController {
    private final UserPlantService userPlantService;
    private PlantRepository plantRepository;
    private final ModelMapper modelMapper;

    @PostMapping("/plantId")
    public Long getUserIdFromUserPlant(@RequestParam Long userPlantId) {
        Plant userPlant = plantRepository.findByAccount_Id(userPlantId);

        return userPlant.getAccount().getId();
    }


//public Long getUserIdFromJwtToken() {
//    Account userDetails = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    return Long.parseLong(userDetails.getName());  // Bu kod username-in userId oldu?unu n?z?rd? tutaraq i?l?yir
//}
//


    @PostMapping("/soilMoisture")
    public ResponseEntity<String> receiveSoilMoistureData(@RequestBody SoilMoistureRequestDto soilMoisture) {
//        SoilMoisture soilMoisture1 = modelMapper.map(soilMoisture, SoilMoisture.class);
//
//        Long userPlantId = soilMoisture1.getCustomerProductId();
//        int humidity = soilMoisture.getMoistureLevel();
//
//        userPlantService.checkAndNotifyUser(userPlantId, humidity);
//
//        return ResponseEntity.ok("Data processed");

        SoilMoisture soilMoisture1 = modelMapper.map(soilMoisture, SoilMoisture.class);

        int humidity = soilMoisture.getMoistureLevel();

        Long userPlantId = soilMoisture1.getCustomerProductId();

        Long userId = getUserIdFromUserPlant(userPlantId);

        userPlantService.checkAndNotifyUser(userId, humidity);

        return ResponseEntity.ok("Data processed");
    }
}

