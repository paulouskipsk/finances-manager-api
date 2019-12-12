package br.edu.uftpr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class DataIntegrityViolationException extends RuntimeException {
	public DataIntegrityViolationException(String message) {
		super(message);
	}
}
