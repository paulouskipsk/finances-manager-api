package br.edu.uftpr.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.uftpr.model.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
	@Query("select e from Expense e where e.description like %?1%")
	List<Expense> findByDescriptionWith(String description);
}
