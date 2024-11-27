package com.ltclab.bloomdoomseller.entity.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ltclab.bloomdoomseller.entity.account.Account;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fertilizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String subCategory;
    @Column(name = "product_type")
    private String type;
    @Column(columnDefinition = "TEXT")
    private String description;

    // indoor, outdoor
    private String usage;
    private double price;

    @Lob
    private byte[] image;

    // Mapping to 'product_id' column
    @ManyToOne
    @JsonIgnore
    private Account account;

//    @OneToMany()
//    private Set<Product> product;
}

