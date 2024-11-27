package com.ltclab.bloomdoomseller.dto.request.accountRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountRequestDto {
    @NotNull(message = "Email cannot be null!")
    public String email;
    @NotNull(message = "Name cannot be null!")
    public String name;
    @NotNull(message = "Surname cannot be null!")
    public String lastName;
    @NotNull(message = "City cannot be null!")
    private String city;
    @NotNull(message = "Password cannot be null!")
    public String password;
    @NotNull(message = "Phone cannot be null!")
    private String phone;
}
