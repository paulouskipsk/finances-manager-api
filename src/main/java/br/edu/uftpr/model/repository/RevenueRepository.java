package br.edu.uftpr.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.uftpr.model.entity.Revenue;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

	@Query("select r from Revenue r where r.description like %?1%")
    List<Revenue> findByDescriptionWith(String description);
}
