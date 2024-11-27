package com.ltclab.bloomdoomseller.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.sensor.Sensor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String subCategory;

    @Column(name = "product_type")
    private String type;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Lob
    private byte[] image;

    private Integer humidity;

    @Column(name = "lighting_angle")
    private double lightingAngle;

    private double price;

    private Long sensorId;
    private String sensorQR;

    @OneToOne(mappedBy = "plant")
    private Sensor sensor;

    @ManyToOne
    @JsonIgnore
    private Account account;

    //    @OneToOne
    //    Product product;

}
