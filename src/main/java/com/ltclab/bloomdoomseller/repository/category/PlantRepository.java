package com.ltclab.bloomdoomseller.repository.category;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Fertilizer;
import com.ltclab.bloomdoomseller.entity.category.Plant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant, Long> {
    Plant findByAccount_Id(Long accountId);
    boolean existsBySubCategory(String subCategory);

    @Query("SELECT p FROM Plant p WHERE LOWER(p.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Plant> findByTypeContainingIgnoreCase(@Param("type") String type, Pageable pageable);

    @Query(value = "SELECT * FROM plant WHERE to_tsvector('english', description) @@ to_tsquery(:keyword)", nativeQuery = true)
    List<Plant> searchByDescription(@Param("keyword") String keyword);

    List<Plant> findByAccount(Account account);

}