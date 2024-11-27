package com.ltclab.bloomdoomseller.repository.account;

import com.ltclab.bloomdoomseller.entity.account.Account;
import com.ltclab.bloomdoomseller.entity.category.Fertilizer;
import com.ltclab.bloomdoomseller.repository.password.PasswordResetToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("SELECT a FROM Account a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Account> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);

    boolean existsByEmailOrPhone(String email, String phone);
    boolean existsByName(String name);
    List<Account> findByCity(String city);
    Optional<Account> findByEmail(String email);
}