package com.ltclab.bloomdoomseller.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.category.*;
import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(name = "account_name")
    private String name;
    private String lastName;
    @Column(unique = true)
    private String phone;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    @Min(value = 8, message = "Password is invalid")
    private String password;
    @Column(nullable = false, name = "account_type")
    private String accountType;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    private SellerDetails sellerDetails;

    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Plant> plants;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Pot> pots;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Seed> seeds;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Fertilizer> fertilizers;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Tool> tools;
    @OneToMany(mappedBy = "account")
    @JsonIgnore
    private List<Sensor> sensors;
}

