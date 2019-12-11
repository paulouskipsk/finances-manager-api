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
import br.edu.utfpr.exception.ResourceAlreadyExistsException;
import br.edu.utfpr.exception.ResourceNotFoundException;

@RestController
@RequestMapping(value = "/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response<UserDTO>> findById(@PathVariable Long id) {
		Response<UserDTO> response = new Response<>();
		
		User user = verifyIfUserExists(id);
		response.setData(new UserDTO(user));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/by-username/{username}")
	public ResponseEntity<Response<UserDTO>> findByUsername(@PathVariable String username) {
		Response<UserDTO> response = new Response<>();
		Optional<User> user = userService.findByUsername(username);

		if (!user.isPresent()) {
			throw new ResourceNotFoundException("Usuário não encontrado com o usuario: "+ username);
		}

		response.setData(new UserDTO(user.get()));
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<Response<List<UserDTO>>> findAll() {
		Response<List<UserDTO>> response = new Response<>();
		List<User> users = userService.findAll();

		if (users.isEmpty()) {
			throw new ResourceNotFoundException("Nenhum usuário encontrado na base de dados");
		}
		
		response.setData(new UserDTO().mapAll(users));
		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/with-pagination")
	public ResponseEntity<Response<Page<UserDTO>>> findAllPagination(
			@PageableDefault(page = 0, size = 3, direction = Direction.ASC) Pageable pageable) {

		Response<Page<UserDTO>> response = new Response<>();
		Page<User> users = userService.findAllPagination(pageable);
		
		if (!users.hasContent()) {
			throw new ResourceNotFoundException("Nenhum usuário encontrado na base de dados");
		}

		response.setData(users.map(user -> new UserDTO(user)));
		return ResponseEntity.ok(response);
	}

	@PostMapping
	public ResponseEntity<Response<UserDTO>> insert(@Valid @RequestBody UserDTO dto, BindingResult result) {
		Response<UserDTO> response = new Response<>();
		
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		if (dto.getId() != null) {
			Optional<User> u = userService.findById(dto.getId());
			if (u.isPresent()) {
				throw new ResourceAlreadyExistsException("Usuário já cadastrado com o ID: "+ dto.getId());
			}
		}
/*
		if (userService.findByUsername(dto.getUsername()).isPresent()) {
			throw new ResourceAlreadyExistsException("Usuário já cadastrado com esta identificação");
		}
*/
		User user = new User(dto);
		user = userService.save(user);
		dto = new UserDTO(user);
		response.setData(dto);
		return ResponseEntity.ok(response);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Response<UserDTO>> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto,
			BindingResult result) {

		Response<UserDTO> response = new Response<>();
		if (result.hasErrors()) {
			response.setErrors(result);
			return ResponseEntity.badRequest().body(response);
		}

		User user = verifyIfUserExists(id);
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

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Response<String>> delete(@PathVariable Long id) {
		Response<String> response = new Response<>();
		
		User user = verifyIfUserExists(id);
		this.userService.delete(user.getId());
		return ResponseEntity.ok(response);
	}
	
	private User verifyIfUserExists(Long id) {
		Optional<User> user = userService.findById(id);
		if(!user.isPresent()) {
			throw new ResourceNotFoundException("Usuário não encontrado para o ID: "+ id);
		}else {
			return user.get();
		}
	}
	/*
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException exception,
                                             HttpServletRequest request) {
//        log.error("Error in process request: " + request.getRequestURL() + " cause by: "
//                + exception.getClass().getSimpleName());
        Response response = new Response();
        response.addError("Oppss!!! Ocorreu um erro de restrições no banco de dados.");

        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
    */
}
