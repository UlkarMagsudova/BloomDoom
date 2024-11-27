package com.ltclab.bloomdoomseller.entity.sensor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Plant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer humidity;

    private Long customerProductId;
    private String sensorQR;

    @OneToOne
    @JoinColumn(name = "plant_id", referencedColumnName = "id")
    @JsonIgnore
    private Plant plant;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnore
    private Account account;
}
