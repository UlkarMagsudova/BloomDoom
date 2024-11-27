package com.ltclab.bloomdoomseller.service.category;


import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.category.PlantRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.PlantResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import com.ltclab.bloomdoomseller.entity.category.Plant;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.InvalidSubcategoryException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.sensor.SensorRepository;
import com.ltclab.bloomdoomseller.repository.category.PlantRepository;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final ModelMapper modelMapper;
    private final SensorRepository sensorRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;

    @Transactional
    public String addPlant(PlantRequestDto plantRequestDto) {
        Plant mapped = modelMapper.map(plantRequestDto, Plant.class);
        Category category = Category.PLANT;
        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && subcat
                        .getSubcategory()
                        .equalsIgnoreCase(plantRequestDto.getSubCategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }
        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && type
                        .getSubcategory()
                        .equalsIgnoreCase(plantRequestDto.getSubCategory()) && type
                        .getType()
                        .equalsIgnoreCase(plantRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidSubcategoryException("Invalid type for the given subcategory");
        }

        boolean check = plantRepository.existsBySubCategory(plantRequestDto.getSubCategory());
        if (check) {
            log.info("Plant with type {} already exists.", plantRequestDto.getSubCategory());
            throw new AlreadyExistsException("Plant with type " + plantRequestDto.getSubCategory() + " already exists.");
        }

        if (plantRequestDto.getSensorQR() != null && !plantRequestDto.getSensorQR().isEmpty()) {
            if (sensorRepository.existsBySensorQRAndPlantIsNotNull(plantRequestDto.getSensorQR())) {
                throw new NotFoundException("This sensor is already linked to another plant.");
            }
            Sensor sensor = sensorRepository.findBySensorQR(plantRequestDto.getSensorQR());
            mapped.setSensor(sensor);
        }

        log.info("Adding plant {}", plantRequestDto);
        plantRepository.save(mapped);
        return "Plant with type " + plantRequestDto.getSubCategory() + " added.";
    }

    public PlantResponseDto readPlantById(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plant with id " + id + " not found."));

        log.info("Plant with ID \"{}\" found.", id);
        return modelMapper.map(plant, PlantResponseDto.class);
    }

    @Transactional(readOnly = true)
    public List<PlantResponseDto> readAllPlants() {
        log.info("Finding all plants from DB.");
        return plantRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, PlantResponseDto.class))
                .toList();
    }

    public String updatePlant(Long id, PlantRequestDto plantRequestDto) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plant with id " + id + " not found."));

        if(!plant.getSubCategory().equals(plantRequestDto.getSubCategory())) {
            if(plantRepository.existsBySubCategory(plantRequestDto.getSubCategory())) {
                throw new UniqueFieldException("Plant subcategory already exists.");
            }
        }

        modelMapper.map(plantRequestDto, plant);
        plantRepository.save(plant);
        log.info("Plant with type {} has been updated.", plantRequestDto.getSubCategory());
        return "Plant with id " + id + " has successfully updated.";
    }

    public String deletePlant(Long id) {
        Plant plant = plantRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Plant with ID " + id + " not found."));

        plantRepository.delete(plant);
        log.info("Plant with id {} has been deleted.", id);
        return "Plant with id " + id + " has been deleted.";
    }

}
