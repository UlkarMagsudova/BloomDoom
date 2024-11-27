package com.ltclab.bloomdoomseller.dto.response.accountResponseDto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountResponseDto {
    private Long id;
    private String email;
    private String name;
    public String lastName;
    private String phone;
    private String city;
}
