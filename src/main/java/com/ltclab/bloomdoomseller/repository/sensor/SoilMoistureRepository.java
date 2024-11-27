package com.ltclab.bloomdoomseller.repository.sensor;

import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import com.ltclab.bloomdoomseller.entity.sensor.SoilMoisture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoilMoistureRepository extends JpaRepository<SoilMoisture, Long> {
    boolean existsBySensorQR(String sensorQR);

    boolean existsBySensorQRAndPlantIsNotNull(String sensorQR);

    Sensor findBySensorQR(String sensorQR);
}
