package com.ltclab.bloomdoomseller.service.category;


import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.category.PotRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.PotResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.category.Pot;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.InvalidSubcategoryException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.category.PotRepository;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PotService {
    private final PotRepository potRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String addPot(PotRequestDto potRequestDto) {
        Pot mapped = modelMapper.map(potRequestDto, Pot.class);
        Category category = Category.POT;

        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && subcat
                        .getSubcategory()
                        .equalsIgnoreCase(potRequestDto.getSubCategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }
        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && type
                        .getSubcategory()
                        .equalsIgnoreCase(potRequestDto.getSubCategory()) && type
                        .getType()
                        .equalsIgnoreCase(potRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidSubcategoryException("Invalid type for the given subcategory");
        }

        boolean check = potRepository.existsBySubCategory(potRequestDto.getSubCategory());
        if (check) {
            log.info("Pot with type {} already exists.", potRequestDto.getSubCategory());
            throw new AlreadyExistsException("Pot with type " + potRequestDto.getSubCategory() + " already exists.");
        }

        log.info("Creating new pot type {}", potRequestDto.getSubCategory());
        potRepository.save(mapped);
        return "Pot type has successfully been added to DB.";
    }

    public PotResponseDto readPotById(Long id) {
        Pot pot = potRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pot with id " + id + " does not exist."));

        log.info("Find pot by id {}", id);
        return modelMapper.map(pot, PotResponseDto.class);
    }

    public List<PotResponseDto> readAllPots() {
        log.info("Finding all pots from DB.");
        return potRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, PotResponseDto.class))
                .toList();
    }

    public String updatePot(Long id, PotRequestDto potRequestDto) {
        Pot pot = potRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pot with id " + id + " does not exist."));

        if(!pot.getSubCategory().equals(potRequestDto.getSubCategory())) {
            if(potRepository.existsBySubCategory(potRequestDto.getSubCategory())) {
                throw new UniqueFieldException("Pot subcategory already exists.");
            }
        }

        modelMapper.map(potRequestDto, pot);
        potRepository.save(pot);
        log.info("Pot with type {} has been updated.", potRequestDto.getSubCategory());
        return "Pot with id " + id + " has successfully updated.";
    }

    public String deletePot(Long id) {
        Pot pot = potRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pot with id " + id + " does not exist."));

        potRepository.delete(pot);
        log.info("Pot with id {} has been deleted.", id);
        return "Pot with id " + id + " has been deleted.";
    }
}
