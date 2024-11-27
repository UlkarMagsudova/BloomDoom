package com.ltclab.bloomdoomseller.repository.category;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Tool;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {
    boolean existsBySubCategory(String subCategory);

    @Query("SELECT t FROM Tool t WHERE LOWER(t.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Tool> findByTypeContainingIgnoreCase(@Param("type") String type, Pageable pageable);

    List<Tool> findByAccount(Account account);

}