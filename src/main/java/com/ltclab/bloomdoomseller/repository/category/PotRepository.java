package com.ltclab.bloomdoomseller.repository.category;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Fertilizer;
import com.ltclab.bloomdoomseller.entity.category.Pot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PotRepository extends JpaRepository<Pot, Long> {
    boolean existsBySubCategory(String subCategory);

    @Query("SELECT p FROM Pot p WHERE LOWER(p.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Pot> findByTypeContainingIgnoreCase(@Param("type") String type, Pageable pageable);

    List<Pot> findByAccount(Account account);
}