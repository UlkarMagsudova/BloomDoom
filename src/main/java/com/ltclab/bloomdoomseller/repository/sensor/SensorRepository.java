package com.ltclab.bloomdoomseller.repository.sensor;

import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    boolean existsBySensorQR(String sensorQR);

    boolean existsBySensorQRAndPlantIsNotNull(String sensorQR);

    Sensor findBySensorQR(String sensorQR);
}
