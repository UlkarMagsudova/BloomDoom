package com.ltclab.bloomdoomseller.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Pot {
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
    private String size;
    private double price;

    @ManyToOne
    @JsonIgnore
    private Account account;
}