package br.edu.uftpr.exception;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import br.edu.uftpr.util.Response;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<?> DataIntegrityViolationException(DataIntegrityViolationException exception,
			HttpServletRequest request) {

		Response response = new Response();
		response.addError("Ocorreu um erro de restrições no banco de dados.");
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleException(EntityNotFoundException exception, HttpServletRequest request) {
		
		Response response = new Response();
		response.addError("O dado solicitado não foi encontrado.");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EntityExistsException.class)
	public ResponseEntity<?> handleException(EntityExistsException exception, HttpServletRequest request) {
		
		Response response = new Response();
		response.addError(exception.getMessage());

		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<?> handleException(EmptyResultDataAccessException exception, HttpServletRequest request) {
		
		Response response = new Response();
		response.addError(exception.getMessage());

		return new ResponseEntity<>(response, HttpStatus.GONE);
	}

	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<?> handleException(NoHandlerFoundException exception, HttpServletRequest request) {
		
		Response response = new Response();
		response.addError("Página não encontrada");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
}
