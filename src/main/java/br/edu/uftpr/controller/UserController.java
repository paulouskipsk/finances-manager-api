package br.edu.uftpr.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uftpr.model.dto.UserDTO;
import br.edu.uftpr.model.entity.User;
import br.edu.uftpr.model.service.UserService;

@RestController
@RequestMapping(value = {"/api/users", "/api/usuarios"})
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(value = { "/{id}", "/{id}" })
	public Optional<User> findById(@PathVariable Long id) {
		return userService.findById(id);
	}

	@GetMapping()
	public List<User> findAll() {
		return userService.findAll();
	}

	@PostMapping(value = { "/insert", "/inserir" })
	public ResponseEntity<Response<UserDTO>> post(@Valid @RequestBody UserDTO dto, BindingResult result) {
		
		

		return null;
	}

	@PostMapping(value = { "/insert-all", "/inserir-todos" })
	public List<User> saveAll(List<User> users) throws DataIntegrityViolationException {
		return userService.saveAll(users);
	}

	@PostMapping(value = { "/update", "/atualizar" })
	public User update(User user) throws DataIntegrityViolationException {
		return userService.save(user);
	}

	@PostMapping(value = { "/update-all", "/atualizar-todos" })
	public List<User> updateAll(List<User> users) throws DataIntegrityViolationException {
		return userService.saveAll(users);
	}

	@DeleteMapping(value = { "/delete/{id}", "/delete/{id}" })
	public void delete(Long id) throws DataIntegrityViolationException {
		userService.delete(id);
	}
}
