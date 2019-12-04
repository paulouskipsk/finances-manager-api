package br.edu.uftpr.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import br.edu.uftpr.model.entity.Expense;
import br.edu.uftpr.model.repository.ExpenseRepository;

@Service
public class ExpenseService {
	@Autowired
	ExpenseRepository expenseRepository;

	public Optional<Expense> findById(Long id) {
		return expenseRepository.findById(id);
	}

	public List<Expense> findAll() {
		return expenseRepository.findAll();
	}

	public Page<Expense> findAllPagination(
			@PageableDefault(page = 0, size = 5, direction = Direction.ASC) Pageable pageable) {

		Page<Expense> expenses = expenseRepository.findAll(pageable);
		return expenses;
	}

	public Expense save(Expense expense) throws DataIntegrityViolationException {
		return expenseRepository.save(expense);
	}

	public List<Expense> saveAll(List<Expense> expenses) throws DataIntegrityViolationException {
		return expenseRepository.saveAll(expenses);
	}

	public void delete(Long id) throws DataIntegrityViolationException {
		expenseRepository.deleteById(id);
	}

	public List<Expense> findByDescriptionWith(String description) {
		return expenseRepository.findByDescriptionWith(description);
	}

}
