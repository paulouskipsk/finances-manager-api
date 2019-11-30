package br.edu.uftpr.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uftpr.model.entity.Revenue;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {

}
