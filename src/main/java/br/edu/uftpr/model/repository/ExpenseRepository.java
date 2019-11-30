package br.edu.uftpr.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.uftpr.model.entity.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

}
