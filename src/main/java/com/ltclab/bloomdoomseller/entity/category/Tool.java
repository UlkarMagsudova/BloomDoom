package com.ltclab.bloomdoomseller.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tool {
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

    // Watering sensing
    private String function;
    private double price;

    @ManyToOne
    @JsonIgnore
    private Account account;
}
