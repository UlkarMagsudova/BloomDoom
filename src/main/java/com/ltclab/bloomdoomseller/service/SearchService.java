package com.ltclab.bloomdoomseller.service;

import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.AccountResponseDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.SellerDetailsResponseDto;
import com.ltclab.bloomdoomseller.dto.response.category.*;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.account.SellerDetails;
import com.ltclab.bloomdoomseller.entity.category.*;
import com.ltclab.bloomdoomseller.repository.SuggestionRepository;
import com.ltclab.bloomdoomseller.repository.account.AccountRepository;
import com.ltclab.bloomdoomseller.repository.account.SellerDetailsRepository;
import com.ltclab.bloomdoomseller.repository.category.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final PlantRepository plantRepository;
    private final PotRepository potRepository;
    private final ToolRepository toolRepository;
    private final FertilizerRepository fertilizerRepository;
    private final SeedRepository seedRepository;
    private final AccountRepository accountRepository;
    private final SellerDetailsRepository sellerDetailsRepository;
    private final ModelMapper modelMapper;



    public Page<PlantResponseDto> searchPlantsByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Plant> plants = plantRepository.findByTypeContainingIgnoreCase(type, pageable);
        return plants.map(plant -> modelMapper.map(plant, PlantResponseDto.class));
    }

    public Page<PotResponseDto> searchPotsByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Pot> pots = potRepository.findByTypeContainingIgnoreCase(type, pageable);
        return pots.map(pot -> modelMapper.map(pot, PotResponseDto.class));
    }

    public Page<FertilizerResponseDto> searchFertilizersByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Fertilizer> fertilizers = fertilizerRepository.findByTypeContainingIgnoreCase(type, pageable);
        return fertilizers.map(fertilizer -> modelMapper.map(fertilizer, FertilizerResponseDto.class));
    }

    public Page<ToolResponseDto> searchToolsByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Tool> tools = toolRepository.findByTypeContainingIgnoreCase(type, pageable);
        return tools.map(tool -> modelMapper.map(tool, ToolResponseDto.class));
    }

    public Page<SeedResponseDto> searchSeedsByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Seed> seeds = seedRepository.findByTypeContainingIgnoreCase(type, pageable);

        return seeds.map(seed -> modelMapper.map(seed, SeedResponseDto.class));
    }

    public Page<AccountResponseDto> searchAccountsByName(String name, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Account> accounts = accountRepository.findByNameContainingIgnoreCase(name, pageable);
        return accounts.map(account -> modelMapper.map(account, AccountResponseDto.class));
    }

    public Page<SellerDetailsResponseDto> searchSellerDetailsByShopName(String shopName, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<SellerDetails> sellerDetails = sellerDetailsRepository.findByShopNameContainingIgnoreCase(shopName, pageable);
        return sellerDetails.map(sd -> modelMapper.map(sd, SellerDetailsResponseDto.class));
    }

    public List<PlantResponseDto> searchPlantsByDescription(String keyword) {
        List<Plant> plants = plantRepository.searchByDescription(keyword);
        return plants.stream()
                .map(plant -> modelMapper.map(plant, PlantResponseDto.class))
                .collect(Collectors.toList());
    }
}
