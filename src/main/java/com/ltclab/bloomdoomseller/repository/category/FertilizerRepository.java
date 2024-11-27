package com.ltclab.bloomdoomseller.repository.category;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Fertilizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FertilizerRepository extends JpaRepository<Fertilizer, Long> {
    boolean existsBySubCategory(String subCategory);

    @Query("SELECT f FROM Fertilizer f WHERE LOWER(f.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Fertilizer> findByTypeContainingIgnoreCase(@Param("type") String type, Pageable pageable);

    List<Fertilizer> findByAccount(Account account);
}
