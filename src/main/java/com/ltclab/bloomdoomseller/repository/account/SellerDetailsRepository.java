package com.ltclab.bloomdoomseller.repository.account;

import com.ltclab.bloomdoomseller.entity.account.SellerDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SellerDetailsRepository extends JpaRepository<SellerDetails, Long> {

    @Query("SELECT sd FROM SellerDetails sd WHERE LOWER(sd.shopName) LIKE LOWER(CONCAT('%', :shopName, '%'))")
    Page<SellerDetails> findByShopNameContainingIgnoreCase(@Param("shopName") String shopName, Pageable pageable);

    boolean existsByShopName(String shopName);

    Optional<SellerDetails> findByShopName(String shopName);

    Optional<SellerDetails> findByAccountId(Long accountId);

}
