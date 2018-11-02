package br.usp.poli.exception;

public class NoRolesUserException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoRolesUserException(String mensagem) {
		super(mensagem);
	}
}