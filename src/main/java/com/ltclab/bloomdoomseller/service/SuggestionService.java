package com.ltclab.bloomdoomseller.service;


import com.ltclab.bloomdoomseller.dto.request.SuggestionRequestDto;
import com.ltclab.bloomdoomseller.dto.response.SuggestionResponseDto;
import com.ltclab.bloomdoomseller.entity.ProductCategory;
import com.ltclab.bloomdoomseller.entity.Suggestion;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.exception.InvalidSubcategoryException;
import com.ltclab.bloomdoomseller.exception.InvalidTypeException;
import com.ltclab.bloomdoomseller.exception.NotFoundException;
import com.ltclab.bloomdoomseller.repository.ProductCategoryRepository;
import com.ltclab.bloomdoomseller.repository.SuggestionRepository;
import com.ltclab.bloomdoomseller.repository.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class SuggestionService {
    private final SuggestionRepository suggestionRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final ProductCategoryService productCategoryService;
    private final ProductCategoryRepository productCategoryRepository;


    @Transactional
    public String createSuggestion(SuggestionRequestDto suggestionRequestDto) {

        String category = "Plant";

        // Check if subcategory exists under the given category
        List<ProductCategory> subcategories = productCategoryService.getAllSubcategories();
        boolean isValidSubcategory = subcategories.stream()
                .anyMatch(subcat -> subcat.getCategory().equalsIgnoreCase(category) &&
                        subcat.getSubcategory().equalsIgnoreCase(suggestionRequestDto.getSubcategory()));
        if (!isValidSubcategory) {
            throw new InvalidSubcategoryException("Invalid subcategory for the given category");
        }

        // Check if type exists under the given subcategory
        List<ProductCategory> types = productCategoryRepository.findAllByTypeIsNotNull();
        boolean isValidType = types.stream()
                .anyMatch(type -> type.getCategory().equalsIgnoreCase(category) &&
                        type.getSubcategory().equalsIgnoreCase(suggestionRequestDto.getSubcategory()) &&
                        type.getType().equalsIgnoreCase(suggestionRequestDto.getType()));
        if (!isValidType) {
            throw new InvalidTypeException("Invalid type for the given subcategory");
        }

        // Map DTO to entity, set fields, and save the suggestion
        Suggestion suggestion = modelMapper.map(suggestionRequestDto, Suggestion.class);
        suggestion.setCreatorId(suggestionRequestDto.getCreatorId());
        suggestion.setCreatedAt(LocalDateTime.now());
        suggestion.setLastEditedAt(null);
        suggestionRepository.save(suggestion);

        return "Suggestion created successfully.";
    }


    public SuggestionResponseDto getSuggestionById(Long id) {

        Suggestion suggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion not found."));

        Account creator = accountRepository.findById(suggestion.getCreatorId())
                .orElseThrow(() -> new NotFoundException("Creator account not found."));

        SuggestionResponseDto response = modelMapper.map(suggestion, SuggestionResponseDto.class);
        response.setCreatorName(creator.getName());
        if (suggestion.getLastEditedAt() != null) {
            response.setLastUpdatedFormatted("Last updated " + getTimeAgoOrDate(suggestion.getLastEditedAt()));
        } else {
            response.setCreatedDateFormatted(getTimeAgoOrDate(suggestion.getCreatedAt()));
        }
        return response;
    }

    public static String getTimeAgoOrDate(LocalDateTime createdDate) {
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(createdDate, now);
        if (duration.toMinutes() < 1) {
            return "Just now";
        } else if (duration.toMinutes() < 60) {
            return duration.toMinutes() + " min ago";
        } else if (duration.toHours() < 24) {
            return duration.toHours() + " hours ago";
        } else if (duration.toDays() < 7) {
            return duration.toDays() + " days ago";
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            return createdDate.format(formatter);
        }
    }

    public List<SuggestionResponseDto> getAllSuggestions() {
        List<Suggestion> suggestions = suggestionRepository.findAll();
        List<SuggestionResponseDto> responseList = new ArrayList<>();
        for (Suggestion suggestion : suggestions) {
            SuggestionResponseDto response = modelMapper.map(suggestion, SuggestionResponseDto.class);
            Account creator = accountRepository.findById(suggestion.getCreatorId())
                    .orElseThrow(() -> new NotFoundException("Creator account not found"));
            response.setCreatorName(creator.getName());
            if (suggestion.getLastEditedAt() != null) {
                response.setLastUpdatedFormatted("Last updated " + getTimeAgoOrDate(suggestion.getLastEditedAt()));
            } else {
                response.setCreatedDateFormatted(getTimeAgoOrDate(suggestion.getCreatedAt()));
            }
            responseList.add(response);
        }
        return responseList;
    }

    public List<SuggestionResponseDto> getSuggestionsByCreatorId(Long creatorId) {
        List<Suggestion> suggestions = suggestionRepository.findByCreatorId(creatorId);
        List<SuggestionResponseDto> responseList = new ArrayList<>();

        Account creator = accountRepository.findById(creatorId)
                .orElseThrow(() -> new NotFoundException("Creator account not found"));

        for (Suggestion suggestion : suggestions) {
            SuggestionResponseDto response = modelMapper.map(suggestion, SuggestionResponseDto.class);

            response.setCreatorName(creator.getName());

            if (suggestion.getLastEditedAt() != null) {
                response.setLastUpdatedFormatted("Last updated " + getTimeAgoOrDate(suggestion.getLastEditedAt()));
            } else {
                response.setCreatedDateFormatted(getTimeAgoOrDate(suggestion.getCreatedAt()));
            }
            responseList.add(response);
        }
        return responseList;
    }

    public String updateSuggestion(Long id, SuggestionRequestDto suggestionRequestDto) {

        Suggestion existingSuggestion = suggestionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));

        if (!existingSuggestion.getCreatorId().equals(suggestionRequestDto.getCreatorId())) {
            throw new RuntimeException("You cannot edit this suggestion");
        }
        existingSuggestion.setSubcategory(suggestionRequestDto.getSubcategory());
        existingSuggestion.setType(suggestionRequestDto.getType());
        existingSuggestion.setContent(suggestionRequestDto.getContent());
        existingSuggestion.setLastEditedAt(LocalDateTime.now());

        suggestionRepository.save(existingSuggestion);
        return "The suggestion updated successfully!";
    }


    public String deleteSuggestionById(Long suggestionId, Long currentUserId) {
        Suggestion suggestion = suggestionRepository.findById(suggestionId)
                .orElseThrow(() -> new NotFoundException("Suggestion not found"));

        if (!suggestion.getCreatorId().equals(currentUserId)) {
            throw new RuntimeException("You are not authorized to delete this suggestion");
        }

        suggestionRepository.delete(suggestion);
        return "The suggestion was deleted successfully.";
    }

    public Page<SuggestionResponseDto> searchSuggestionsByType(String type, int page, int size, String sortField, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortField);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Suggestion> suggestions = suggestionRepository.findByTypeContainingIgnoreCase(type, pageable);
        return suggestions.map(suggestion -> modelMapper.map(suggestion, SuggestionResponseDto.class));
    }

    public List<SuggestionResponseDto> searchSuggestionsByContent(String keyword) {
        List<Suggestion> suggestions = suggestionRepository.searchByContent(keyword);
        return suggestions.stream()
                .map(suggestion -> modelMapper.map(suggestion, SuggestionResponseDto.class))
                .collect(Collectors.toList());
    }

}
