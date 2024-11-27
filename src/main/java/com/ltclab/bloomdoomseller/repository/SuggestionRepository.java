package com.ltclab.bloomdoomseller.repository;

import com.ltclab.bloomdoomseller.entity.Suggestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByCreatorId(Long creatorId);

    @Query(value = "SELECT * FROM suggestions WHERE MATCH(content) AGAINST (:searchTerm IN NATURAL LANGUAGE MODE)", nativeQuery = true)
    List<Suggestion> searchByContent(@Param("searchTerm") String searchTerm);

    @Query("SELECT sg FROM Suggestion sg WHERE LOWER(sg.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Suggestion> findByTypeContainingIgnoreCase(@Param("type") String type, Pageable pageable);
}
