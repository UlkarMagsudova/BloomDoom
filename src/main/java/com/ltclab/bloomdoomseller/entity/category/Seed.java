package com.ltclab.bloomdoomseller.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subCategory;
    @Column(name = "product_type")
    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;
    //Spring, Fall
    private String plantingSeason;
    //Full Sun, Partial Shade
    private String sunlightRequirement;
    //Moderate, High
    private String wateringRequirement;
    //Loamy, Sandy, Well-drained
    private String soilType;
    private BigDecimal price;
    private Integer quantity;
    private byte[] image;
    private Double rating;

    @ManyToOne
    @JsonIgnore
    private Account account;
}