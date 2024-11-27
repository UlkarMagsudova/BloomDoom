package com.ltclab.bloomdoomseller.service.category;

import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.category.SeedRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.SeedResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.category.Seed;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.InvalidSubcategoryException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.category.SeedRepository;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeedService {
    private final SeedRepository seedRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String addSeed(SeedRequestDto seedRequestDto) {
        Seed mapped = modelMapper.map(seedRequestDto, Seed.class);

        Category category = Category.SEED;
        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && subcat
                        .getSubcategory()
                        .equalsIgnoreCase(seedRequestDto.getSubCategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }
        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && type
                        .getSubcategory()
                        .equalsIgnoreCase(seedRequestDto.getSubCategory()) && type
                        .getType()
                        .equalsIgnoreCase(seedRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidSubcategoryException("Invalid type for the given subcategory");
        }

        boolean check = seedRepository.existsBySubCategory(seedRequestDto.getSubCategory());

        if (check) {
            log.info("Seed already exists with type {}", seedRequestDto.getSubCategory());
            throw new AlreadyExistsException("Seed already exists with type " + seedRequestDto.getSubCategory());
        }

        log.info("Adding seed with type {}", seedRequestDto.getSubCategory());
        seedRepository.save(mapped);
        return "Seed type has successfully been added to DB.";

    }

    public SeedResponseDto readSeedById(Long id) {
        Seed seed = seedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seed with id " + id + " does not exist."));

        log.info("Find Seed by id {}", id);
        return modelMapper.map(seed, SeedResponseDto.class);
    }

    public List<SeedResponseDto> readAllSeeds() {
        log.info("Finding all seeds from DB.");
        return seedRepository.
                findAll()
                .stream()
                .map(m -> modelMapper.map(m, SeedResponseDto.class))
                .toList();
    }

    public String updateSeed(Long id, SeedRequestDto seedRequestDto) {
        Seed seed = seedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seed with id " + id + " does not exist."));

        if(!seed.getSubCategory().equals(seedRequestDto.getSubCategory())) {
            if(seedRepository.existsBySubCategory(seedRequestDto.getSubCategory())) {
                throw new UniqueFieldException("Seed subcategory already exists.");
            }
        }

        modelMapper.map(seedRequestDto, seed);
        seedRepository.save(seed);
        log.info("Seed with type {} has been updated.", seedRequestDto.getSubCategory());
        return "Seed with id " + id + " has successfully updated.";
    }

    public String deleteSeed(Long id) {
        Seed seed = seedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Seed with id " + id + " does not exist."));

        seedRepository.delete(seed);
        log.info("Seed with id {} has been deleted.", id);
        return "Seed with id " + id + " has been deleted.";
    }

}
