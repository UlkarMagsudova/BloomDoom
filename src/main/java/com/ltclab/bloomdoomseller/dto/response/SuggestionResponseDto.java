package com.ltclab.bloomdoomseller.dto.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SuggestionResponseDto {
    private Long id;
    private String subcategory;
    private String suggestionType;
    private String content;
//    private LocalDateTime createdDate;
    private String createdDateFormatted;
    private String lastUpdatedFormatted;;
    private String creatorName;

}

