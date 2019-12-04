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

import br.edu.uftpr.model.dto.UserDTO;
import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.service.UserService;
import br.edu.uftpr.util.Response;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = "/find/{id}")
	public ResponseEntity<Response<UserDTO>> findById(@PathVariable Long id) {
		Response<UserDTO> response = new Response<>();
		Optional<User> user = userService.findById(id);

		if (!user.isPresent()) {
			response.addError("Usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(new UserDTO(user.get()));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/find-by-username/{username}")
	public ResponseEntity<Response<UserDTO>> findByUsername(@PathVariable String username) {
		Response<UserDTO> response = new Response<>();
		Optional<User> user = userService.findByUsername(username);

		if (!user.isPresent()) {
			response.addError("Usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		
		response.setData(new UserDTO(user.get()));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/find-all")
	public ResponseEntity<Response<List<UserDTO>>> findAll() {
		Response<List<UserDTO>> response = new Response<>();
		List<User> users = userService.findAll();

		if (users.isEmpty()) {
			response.addError("Nenhum usuário não encontrado");
			return ResponseEntity.badRequest().body(response);
		}
		List<UserDTO> userDTOs = new UserDTO().mapAll(users);
		response.setData(userDTOs);
		
		return ResponseEntity.ok(response);	
	}
	
	@GetMapping(value = "/find-all-pagination")
    public ResponseEntity<Response<Page<UserDTO>>> findAllPagination(
    		@PageableDefault(page=0, size=3, direction = Direction.ASC) Pageable pageable) {        

        Response<Page<UserDTO>> response = new Response<>();       
        Page<User> users = userService.findAllPagination(pageable);
        
        Page<UserDTO> userDTOs = users.map(user -> new UserDTO(user));
        response.setData(userDTOs);
                
        return ResponseEntity.ok(response);
    }
	
	@PostMapping(value = "/insert")
	public ResponseEntity<Response<UserDTO>> insert(@Valid @RequestBody UserDTO dto, BindingResult result) {
		Response<UserDTO> response = new Response<>();

		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		if (dto.getId() != null) {
			Optional<User> u = userService.findById(dto.getId());
			if (u.isPresent()) {
				response.addError("Usuário já cadastrado");
				return ResponseEntity.badRequest().body(response);
			}
		}

		if (userService.findByUsername(dto.getUsername()) != null) {
			response.addError("Usuário já cadastrado com esta identificação");
			return ResponseEntity.badRequest().body(response);
		}

		User user = new User(dto);
		user = userService.save(user);
		dto = new UserDTO(user);
		response.setData(dto);
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Response<UserDTO>> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto, BindingResult result) {

		Response<UserDTO> response = new Response<>();
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		Optional<User> u = userService.findById(id);
		if (!u.isPresent()) {
			response.addError("Usuario não encontrado com este Código");
			return ResponseEntity.badRequest().body(response);
		}
		User user = u.get();

		
		if (!user.getUsername().equals(dto.getUsername())) {
			response.addError("campo usuario não pode ser alterado, impossível atualizar registro");
			return ResponseEntity.badRequest().body(response);
		}

		user.update(dto);
		user = userService.save(user);
		dto = new UserDTO(user);
		response.setData(dto);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {

		Response<String> response = new Response<>();
		Optional<User> u = userService.findById(id);

		if (!u.isPresent()) {
			response.addError("Erro ao remover, registro não encontrado para o id " + id);
			return ResponseEntity.badRequest().body(response);
		}

		this.userService.delete(id);
		return ResponseEntity.ok(response);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException exception,
                                             HttpServletRequest request) {
 /*       log.error("Error in process request: " + request.getRequestURL() + " cause by: "
                + exception.getClass().getSimpleName());*/
        Response response = new Response();
        response.addError("Ocorreu um erro de restrições no banco de dados.");

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
