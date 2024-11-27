package com.ltclab.bloomdoomseller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuggestionRequestDto {
    @NotNull(message = "Subcategory cannot be null!")
    private String subcategory;
    @NotNull(message = "Type cannot be null!")
    private String type;
    @NotNull(message = "Content cannot be null!")
    private String content;
    @NotNull(message = "Creator ID cannot be null!")
    private Long creatorId;
}
