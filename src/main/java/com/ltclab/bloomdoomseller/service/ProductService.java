package com.ltclab.bloomdoomseller.service;

import com.ltclab.bloomdoomseller.constant.Category;
import com.ltclab.bloomdoomseller.dto.request.ProductRequestDto;
import com.ltclab.bloomdoomseller.dto.response.ProductResponseDto;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.account.SellerDetails;
import com.ltclab.bloomdoomseller.repository.account.AccountRepository;
import com.ltclab.bloomdoomseller.repository.account.SellerDetailsRepository;
import com.ltclab.bloomdoomseller.repository.category.*;
import com.ltclab.bloomdoomseller.service.category.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductService {
    private final PlantService plantService;
    private final PotService potService;
    private final ToolService toolService;
    private final FertilizerService fertilizerService;
    private final SeedService seedService;
    private final FertilizerRepository fertilizerRepository;
    private final PlantRepository plantRepository;
    private final PotRepository potRepository;
    private final SeedRepository seedRepository;
    private final ToolRepository toolRepository;
    private final SellerDetailsRepository sellerDetailsRepository;
    private final ModelMapper modelMapper;
    private final AccountRepository accountRepository;

    @Transactional
    public String createProduct(Category category, ProductRequestDto productRequestDto) {
        return switch (category) {
            case PLANT -> plantService.addPlant(productRequestDto.getPlantRequestDto());
            case POT -> potService.addPot(productRequestDto.getPotRequestDto());
            case TOOL -> toolService.addTool(productRequestDto.getToolRequestDto());
            case FERTILIZER -> fertilizerService.addFertilizer(productRequestDto.getFertilizerRequestDto());
            case SEED -> seedService.addSeed(productRequestDto.getSeedRequestDto());
            default -> throw new IllegalArgumentException("Invalid subcategory");
        };
    }

    public String updateProduct(Category category, Long productId, ProductRequestDto productRequestDto) {
        return switch (category) {
            case PLANT -> plantService.updatePlant(productId, productRequestDto.getPlantRequestDto());
            case POT -> potService.updatePot(productId, productRequestDto.getPotRequestDto());
            case TOOL -> toolService.updateTool(productId, productRequestDto.getToolRequestDto());
            case FERTILIZER ->
                    fertilizerService.updateFertilizer(productId, productRequestDto.getFertilizerRequestDto());
            case SEED -> seedService.updateSeed(productId, productRequestDto.getSeedRequestDto());
            default -> throw new IllegalArgumentException("Invalid subcategory");
        };
    }

    public List<?> getProductsByCategory(Category category) {
        switch (category) {
            case PLANT:
                return plantService.readAllPlants(); // Returns List<PlantResponseDto>
            case POT:
                return potService.readAllPots(); // Returns List<PotResponseDto>
            case TOOL:
                return toolService.readAllTools(); // Returns List<ToolResponseDto>
            case FERTILIZER:
                return fertilizerService.readAllFertilizers(); // Returns List<FertilizerResponseDto>
            default:
                throw new IllegalArgumentException("Invalid subcategory");
        }
    }
    public List<ProductResponseDto> getAllProducts() {
        List<ProductResponseDto> allProducts = new ArrayList<>();

        allProducts.addAll(plantService.readAllPlants().stream()
                .map(plant -> modelMapper.map(plant, ProductResponseDto.class))
                .collect(Collectors.toList()));
        allProducts.addAll(potService.readAllPots().stream()
                .map(pot -> modelMapper.map(pot, ProductResponseDto.class))
                .collect(Collectors.toList()));
        allProducts.addAll(toolService.readAllTools().stream()
                .map(tool -> modelMapper.map(tool, ProductResponseDto.class))
                .collect(Collectors.toList()));
        allProducts.addAll(fertilizerService.readAllFertilizers().stream()
                .map(fertilizer -> modelMapper.map(fertilizer, ProductResponseDto.class))
                .collect(Collectors.toList()));

        return allProducts;
    }

    public Object getProductById(Category category, Long productId) {
        return switch (category) {
            case PLANT -> plantService.readPlantById(productId); // Returns PlantResponseDto
            case POT -> potService.readPotById(productId); // Returns PotResponseDto
            case TOOL -> toolService.readToolById(productId); // Returns ToolResponseDto
            case FERTILIZER -> fertilizerService.readFertilizerById(productId); // Returns FertilizerResponseDto
            default -> throw new IllegalArgumentException("Invalid subcategory");
        };
    }

    public String deleteProduct(Category category, Long productId) {
        return switch (category) {
            case PLANT -> plantService.deletePlant(productId);
            case POT -> potService.deletePot(productId);
            case TOOL -> toolService.deleteTool(productId);
            case FERTILIZER -> fertilizerService.deleteFertilizer(productId);
            case SEED -> seedService.deleteSeed(productId);
            default -> throw new IllegalArgumentException("Invalid subcategory");
        };
    }

    public List<ProductResponseDto> getProductsByAccount(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found with ID: " + accountId));

        return aggregateProductsByAccount(account);
    }

    public List<ProductResponseDto> getProductsByShopName(String shopName) {
        SellerDetails sellerDetails = sellerDetailsRepository.findByShopName(shopName)
                .orElseThrow(() -> new EntityNotFoundException("Seller with shop name not found: " + shopName));

        return aggregateProductsByAccount(sellerDetails.getAccount());
    }

    public List<ProductResponseDto> getProductsByCity(String city) {
        List<Account> accountsInCity = accountRepository.findByCity(city);
        if (accountsInCity.isEmpty()) {
            throw new EntityNotFoundException("No accounts found in the specified city: " + city);
        }
        // Aggregate products from all accounts in the specified city
        return accountsInCity.stream()
                .flatMap(account -> aggregateProductsByAccount(account).stream())
                .collect(Collectors.toList());
    }

    private List<ProductResponseDto> aggregateProductsByAccount(Account account) {
        List<ProductResponseDto> products = new ArrayList<>();

        fertilizerRepository.findByAccount(account)
                .forEach(fertilizer -> products.add(modelMapper.map(fertilizer, ProductResponseDto.class)));

        plantRepository.findByAccount(account)
                .forEach(plant -> products.add(modelMapper.map(plant, ProductResponseDto.class)));

        potRepository.findByAccount(account)
                .forEach(pot -> products.add(modelMapper.map(pot, ProductResponseDto.class)));

        seedRepository.findByAccount(account)
                .forEach(seed -> products.add(modelMapper.map(seed, ProductResponseDto.class)));

        toolRepository.findByAccount(account)
                .forEach(tool -> products.add(modelMapper.map(tool, ProductResponseDto.class)));

        return products;
    }
}