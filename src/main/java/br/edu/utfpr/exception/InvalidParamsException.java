package br.edu.utfpr.exception;

public class InvalidParamsException extends IllegalArgumentException {
	private static final long serialVersionUID = 1L;

	public InvalidParamsException() {
		super("Os parâmetros são inválidos. ");
	}
}
