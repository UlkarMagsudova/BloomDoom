package com.ltclab.bloomdoomseller.service.accountService;


import com.ltclab.bloomdoomseller.dto.request.accountRequestDto.SellerDetailsRequestDto;
import com.ltclab.bloomdoomseller.dto.response.accountResponseDto.SellerDetailsResponseDto;
import com.ltclab.bloomdoomseller.entity.account.SellerDetails;
import com.ltclab.bloomdoomseller.exception.AlreadyExistsException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.exception.UniqueFieldException;
import com.ltclab.bloomdoomseller.repository.account.SellerDetailsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SellerDetailsService {
    private final SellerDetailsRepository sellerDetailsRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public String createSellerDetails(SellerDetailsRequestDto sellerDetailsRequestDto) {
        SellerDetails sellerDetails = modelMapper.map(sellerDetailsRequestDto, SellerDetails.class);
        Optional<SellerDetails> existingSellerDetails = sellerDetailsRepository.findByAccountId(sellerDetailsRequestDto.getAccountId());
        if (existingSellerDetails.isPresent()) {
            log.info("Seller already exists with account {}", sellerDetailsRequestDto.getAccountId());
            throw new AlreadyExistsException("SellerDetails already exists for account ID " + sellerDetailsRequestDto.getAccountId());
        }
        log.info("Adding seller with account type {}", sellerDetailsRequestDto.getAccountId());
        sellerDetailsRepository.save(sellerDetails);
        return "Seller created successfully";
    }

    public List<SellerDetailsResponseDto> getAllMySellerDetails() {
        log.info("Finding all sellers from DB.");
        List<SellerDetails> sellerDetails = sellerDetailsRepository.findAll();
        return sellerDetails
                .stream()
                .map(sellerDetail -> modelMapper.map(sellerDetail, SellerDetailsResponseDto.class))
                .collect(Collectors.toList());
    }

    public SellerDetailsResponseDto getSellerDetailsById(Long id) {
        SellerDetails sellerDetails = sellerDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SellerDetails Not Found by ID- " + id));

        log.info("Find seller by id {}", id);
        return modelMapper.map(sellerDetails, SellerDetailsResponseDto.class);
    }

    public SellerDetailsResponseDto getSellerDetailsByShopName(String shopName) {
        SellerDetails sellerDetails = sellerDetailsRepository.findByShopName(shopName)
                .orElseThrow(() -> new NotFoundException("SellerDetails Not Found by Shop name- " + shopName));

        log.info("Finding seller by shop name {}", shopName);
        return modelMapper.map(sellerDetails, SellerDetailsResponseDto.class);
    }

    public SellerDetailsResponseDto getSellerDetailsByAccountId(Long accountId) {
        SellerDetails sellerDetails = sellerDetailsRepository.findByAccountId(accountId)
                .orElseThrow(() -> new NotFoundException("Seller Details Not Found by Account " + accountId));
        log.info("Find seller by account {}", accountId);
        return modelMapper.map(sellerDetails, SellerDetailsResponseDto.class);
    }

    public String updateSellerDetails(SellerDetailsRequestDto sellerDetailsRequestDto, Long id) {
        SellerDetails sellerDetails = sellerDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("SellerDetails Not Found by ID- " + id));
        if (!sellerDetails.getShopName().equals(sellerDetailsRequestDto.getShopName())) {
            if (sellerDetailsRepository.existsByShopName(sellerDetailsRequestDto.getShopName())) {
                throw new UniqueFieldException("Seller shop name already exists.");
            }
        }
        modelMapper.map(sellerDetailsRequestDto, sellerDetails);
        sellerDetails.setId(id);
        sellerDetailsRepository.save(sellerDetails);
        log.info("Seller details with shop name {} has been updated.", sellerDetailsRequestDto.getShopName());
        return "Seller Details updated successfully";
    }

    public String deleteSellerDetails(Long id) {
        SellerDetails byId = sellerDetailsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("My Product Not Found by ID- " + id));

        log.info("Seller with id {} has been deleted.", id);
        sellerDetailsRepository.delete(byId);
        return "Seller Details deleted successfully";
    }

    public String deleteAllSellerDetails() {
        sellerDetailsRepository.deleteAll();
        log.info("All seller details have been deleted.");
        return "All Seller Details deleted successfully";
    }
}
