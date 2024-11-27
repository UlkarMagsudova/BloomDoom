package com.ltclab.bloomdoomseller.service.category;

import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.category.ToolRequestDto;
import com.ltclab.bloomdoomseller.dto.response.category.ToolResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.category.Tool;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.InvalidSubcategoryException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.category.ToolRepository;
import com.ltclab.bloomdoomseller.service.ProductCategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToolService {
    private final ToolRepository toolRepository;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String addTool(ToolRequestDto toolRequestDto) {
        Tool mapped = modelMapper.map(toolRequestDto, Tool.class);

        Category category = Category.TOOL;
        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && subcat
                        .getSubcategory()
                        .equalsIgnoreCase(toolRequestDto.getSubCategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }
        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type
                        .getCategory()
                        .equalsIgnoreCase(category.name()) && type
                        .getSubcategory()
                        .equalsIgnoreCase(toolRequestDto.getSubCategory()) && type
                        .getType()
                        .equalsIgnoreCase(toolRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidSubcategoryException("Invalid type for the given subcategory");
        }

        boolean check = toolRepository.existsBySubCategory(toolRequestDto.getSubCategory());
        if (check) {
            log.info("Tool already exists with type {}.", toolRequestDto.getSubCategory());
            throw new AlreadyExistsException("Tool already exists with type " + toolRequestDto.getSubCategory());
        }

        toolRepository.save(mapped);
        log.info("Adding tool with type {}.", toolRequestDto.getSubCategory());
        return "Tool type has successfully been added to DB.";
    }

    public ToolResponseDto readToolById(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tool not found by id " + id + "."));

        log.info("Tool found with id {}.", id);
        return modelMapper.map(tool, ToolResponseDto.class);
    }

    public List<ToolResponseDto> readAllTools() {
        log.info("Finding all tools from DB.");
        return toolRepository
                .findAll()
                .stream()
                .map(m -> modelMapper.map(m, ToolResponseDto.class))
                .toList();
    }

    public String updateTool(Long id, ToolRequestDto toolRequestDto) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tool not found by id " + id + "."));

        if(!tool.getSubCategory().equals(toolRequestDto.getSubCategory())) {
            if(toolRepository.existsBySubCategory(toolRequestDto.getSubCategory())) {
                throw new UniqueFieldException("Tool subcategory already exists.");
            }
        }

        modelMapper.map(toolRequestDto, tool);
        toolRepository.save(tool);
        log.info("Tool with name {} has been updated.", toolRequestDto.getSubCategory());
        return "Tool with id " + id + " has successfully updated.";
    }

    public String deleteTool(Long id) {
        Tool tool = toolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Tool not found by id " + id + "."));

        toolRepository.delete(tool);
        log.info("Tool with id {} has been deleted.", id);
        return "Tool has successfully been deleted.";
    }
}

