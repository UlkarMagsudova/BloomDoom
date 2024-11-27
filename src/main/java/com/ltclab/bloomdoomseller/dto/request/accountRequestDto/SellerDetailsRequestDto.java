package com.ltclab.bloomdoomseller.dto.request.accountRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SellerDetailsRequestDto {
    @NotNull(message = "Shop name cannot be null!")
    private String shopName;
    private String address;
    private Long accountId;
}
