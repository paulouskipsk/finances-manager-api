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
import br.edu.uftpr.model.dto.RevenueDTO;
import br.edu.uftpr.model.entity.Revenue;
import br.edu.uftpr.model.service.RevenueService;
import br.edu.uftpr.util.Response;

@RestController
@RequestMapping(value = "/api/revenues")
@CrossOrigin(origins = "*")
public class RevenueController {

	@Autowired
	RevenueService revenueService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<RevenueDTO>> findById(@PathVariable Long id) {
		Response<RevenueDTO> response = new Response<>();

		Revenue revenue = verifyIfRevenueExists(id);
		response.setData(new RevenueDTO(revenue));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/by-description/{description}")
	public ResponseEntity<Response<List<RevenueDTO>>> findByDescriptionWith(@PathVariable String description) {
		Response<List<RevenueDTO>> response = new Response<>();
		List<Revenue> revenues = revenueService.findByDescriptionWith(description);

		if (revenues.isEmpty()) {
			response.addError("Nenhuma Receita encontrada");
			return ResponseEntity.badRequest().body(response);
		}

		List<RevenueDTO> revenueDTOs = new RevenueDTO().mapAll(revenues);
		response.setData(revenueDTOs);
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<RevenueDTO>>> findAll() {
		Response<List<RevenueDTO>> response = new Response<>();
		List<Revenue> revenues = revenueService.findAll();

		if (revenues.isEmpty()) {
			throw new ResourceNotFoundException("Nenhuma receita encontrada na base de dados");
		}

		List<RevenueDTO> revenueDTOs = new RevenueDTO().mapAll(revenues);
		response.setData(revenueDTOs);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/with-pagination")
	public ResponseEntity<Response<Page<RevenueDTO>>> findAllPagination(
			@PageableDefault(page = 0, size = 3, direction = Direction.ASC) Pageable pageable) {

		Response<Page<RevenueDTO>> response = new Response<>();
		Page<Revenue> revenues = revenueService.findAllPagination(pageable);
		Page<RevenueDTO> revenueDTOs = revenues.map(revenue -> new RevenueDTO(revenue));
		response.setData(revenueDTOs);

		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<RevenueDTO>> insert(@Valid @RequestBody RevenueDTO dto, BindingResult result) {
		Response<RevenueDTO> response = new Response<>();

		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		if (dto.getId() != null) {
			Optional<Revenue> revenue = revenueService.findById(dto.getId());
			if (revenue.isPresent()) {
				throw new ResourceAlreadyExistsException("Receita já cadastrada com este ID: " + revenue.get().getId());
			}
		}

		Revenue revenue = new Revenue(dto);
		revenue = revenueService.save(revenue);
		dto = new RevenueDTO(revenue);
		response.setData(dto);
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<RevenueDTO>> update(@PathVariable Long id, @Valid @RequestBody RevenueDTO dto,
			BindingResult result) {

		Response<RevenueDTO> response = new Response<>();
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		Revenue revenue = verifyIfRevenueExists(id);
		revenue.update(dto);
		revenue = revenueService.save(revenue);
		dto = new RevenueDTO(revenue);
		response.setData(dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		Revenue revenue = verifyIfRevenueExists(id);
		this.revenueService.delete(revenue.getId());

		return ResponseEntity.ok(response);
	}

	private Revenue verifyIfRevenueExists(Long id) {
		Optional<Revenue> revenue = revenueService.findById(id);
		if (!revenue.isPresent()) {
			throw new ResourceNotFoundException("Receita não encontrada para o ID: " + id);
		} else {
			return revenue.get();
		}
	}
}
