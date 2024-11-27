package com.ltclab.bloomdoomseller.controller.sensor;

import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.category.Plant;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.category.PlantRepository;
import com.ltclab.bloomdoomseller.service.sensor.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

// UserPlantService.java
@Service
@RequiredArgsConstructor
public class UserPlantService {
    private PlantRepository userPlantRepository;
    private NotificationService notificationService;

    public void checkAndNotifyUser(Long userPlantId, int currentHumidity) {
        Optional<Plant> optionalUserPlant = userPlantRepository.findById(userPlantId);

        if (optionalUserPlant.isPresent()) {
            Plant userPlant = optionalUserPlant.get();

            int optimalHumidityLevel = userPlant.getHumidity();

            if (currentHumidity < optimalHumidityLevel) {
                // Istifade edir notification gonderin
                notificationService.sendNotification("Your plant " + userPlant.getType() + " needs watering!", userPlant.getAccount().getName());
            }

//            userPlant.setCurrentHumidityLevel(currentHumidity);
            userPlantRepository.save(userPlant);
        }
    }
}

