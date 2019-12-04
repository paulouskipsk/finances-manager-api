package br.edu.uftpr.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<Response<RevenueDTO>> findById(@PathVariable Long id) {
		Response<RevenueDTO> response = new Response<>();
		Optional<Revenue> revenue = revenueService.findById(id);

		if (!revenue.isPresent()) {
			response.addError("Receita não encontrada com este código: "+ id);
			return ResponseEntity.badRequest().body(response);
		}

		response.setData(new RevenueDTO(revenue.get()));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/find-by-description/{description}")
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

	@GetMapping(value = "/find-all")
	public ResponseEntity<Response<List<RevenueDTO>>> findAll() {
		Response<List<RevenueDTO>> response = new Response<>();
		List<Revenue> revenues = revenueService.findAll();

		if (revenues.isEmpty()) {
			response.addError("Nenhum usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		List<RevenueDTO> revenueDTOs = new RevenueDTO().mapAll(revenues);
		response.setData(revenueDTOs);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/find-all-pagination")
	public ResponseEntity<Response<Page<RevenueDTO>>> findAllPagination(
			@PageableDefault(page = 0, size = 3, direction = Direction.ASC) Pageable pageable) {

		Response<Page<RevenueDTO>> response = new Response<>();
		Page<Revenue> revenues = revenueService.findAllPagination(pageable);

		Page<RevenueDTO> revenueDTOs = revenues.map(revenue -> new RevenueDTO(revenue));
		response.setData(revenueDTOs);

		return ResponseEntity.ok(response);
	}

	@PostMapping(value = "/insert")
	public ResponseEntity<Response<RevenueDTO>> insert(@Valid @RequestBody RevenueDTO dto, BindingResult result) {
		Response<RevenueDTO> response = new Response<>();

		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		if (dto.getId() != null) {
			Optional<Revenue> r = revenueService.findById(dto.getId());
			if (r.isPresent()) {
				response.addError("Receita já cadastrada com este código");
				return ResponseEntity.badRequest().body(response);
			}
		}

		Revenue revenue = new Revenue(dto);
		revenue = revenueService.save(revenue);
		dto = new RevenueDTO(revenue);
		response.setData(dto);
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Response<RevenueDTO>> update(@PathVariable Long id, @Valid @RequestBody RevenueDTO dto,
			BindingResult result) {

		Response<RevenueDTO> response = new Response<>();
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		Optional<Revenue> u = revenueService.findById(id);
		if (!u.isPresent()) {
			response.addError("Receita não encontrada com este Código");
			return ResponseEntity.badRequest().body(response);
		}
		Revenue revenue = u.get();

		revenue.update(dto);
		revenue = revenueService.save(revenue);
		dto = new RevenueDTO(revenue);
		response.setData(dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {

		Response<String> response = new Response<>();
		Optional<Revenue> u = revenueService.findById(id);

		if (!u.isPresent()) {
			response.addError("Erro ao remover, registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.revenueService.delete(id);
		return ResponseEntity.ok(response);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> handleException(DataIntegrityViolationException exception, HttpServletRequest request) {
		/*
		 * log.error("Error in process request: " + request.getRequestURL() +
		 * " cause by: " + exception.getClass().getSimpleName());
		 */
		Response response = new Response();
		response.addError("Ocorreu um erro de restrições no banco de dados.");

		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
}
