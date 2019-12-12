package br.edu.uftpr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uftpr.exception.ResourceAlreadyExistsException;
import br.edu.uftpr.exception.ResourceNotFoundException;
import br.edu.uftpr.model.dto.ExpenseDTO;
import br.edu.uftpr.model.entity.Expense;
import br.edu.uftpr.model.service.ExpenseService;
import br.edu.uftpr.util.Response;

@RestController
@RequestMapping(value = "/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

	@Autowired
	ExpenseService expenseService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<ExpenseDTO>> findById(@PathVariable Long id) {
		Response<ExpenseDTO> response = new Response<>();
		
		Expense expense = verifyIfExpenseExists(id);
		response.setData(new ExpenseDTO(expense));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/by-description/{description}")
	public ResponseEntity<Response<List<ExpenseDTO>>> findByDescriptionWith(@PathVariable String description) {
		Response<List<ExpenseDTO>> response = new Response<>();
		List<Expense> expenses = expenseService.findByDescriptionWith(description);

		if (expenses.isEmpty()) {
			throw new ResourceNotFoundException("Despesa não encontrada com esta descrição: "+description);
		}

		List<ExpenseDTO> expenseDTOs = new ExpenseDTO().mapAll(expenses);
		response.setData(expenseDTOs);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<ExpenseDTO>>> findAll() {
		Response<List<ExpenseDTO>> response = new Response<>();
		List<Expense> expenses = expenseService.findAll();

		if (expenses.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma Despesa encontrada na base de dados");
		}
		List<ExpenseDTO> expenseDTOs = new ExpenseDTO().mapAll(expenses);
		response.setData(expenseDTOs);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/with-pagination")
	public ResponseEntity<Response<Page<ExpenseDTO>>> findAllPagination(
			@PageableDefault(page = 0, size = 3, direction = Direction.ASC) Pageable pageable) {

		Response<Page<ExpenseDTO>> response = new Response<>();
		Page<Expense> expenses = expenseService.findAllPagination(pageable);

		Page<ExpenseDTO> expenseDTOs = expenses.map(expense -> new ExpenseDTO(expense));
		response.setData(expenseDTOs);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<ExpenseDTO>> insert(@Valid @RequestBody ExpenseDTO dto, BindingResult result) {
		Response<ExpenseDTO> response = new Response<>();

		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		if (dto.getId() != null) {
			Optional<Expense> r = expenseService.findById(dto.getId());
			if (r.isPresent()) {
				throw new ResourceAlreadyExistsException("Despesa já cadastrada com este ID: "+ dto.getId());
			}
		}

		Expense expense = new Expense(dto);
		expense = expenseService.save(expense);
		dto = new ExpenseDTO(expense);
		response.setData(dto);
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<ExpenseDTO>> update(@PathVariable Long id, @Valid @RequestBody ExpenseDTO dto,
			BindingResult result) {

		Response<ExpenseDTO> response = new Response<>();
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		Expense expense = verifyIfExpenseExists(id);
		expense.update(dto);
		expense = expenseService.save(expense);
		dto = new ExpenseDTO(expense);
		response.setData(dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		
		Expense expense = verifyIfExpenseExists(id);
		this.expenseService.delete(expense.getId());
		return ResponseEntity.ok(response);
	}
	
	private Expense verifyIfExpenseExists(Long id) {
		Optional<Expense> expense = expenseService.findById(id);
		if (!expense.isPresent()) {
			throw new ResourceNotFoundException("Despesa não encontrada para o ID: " + id);
		} else {
			return expense.get();
		}
	}
}
