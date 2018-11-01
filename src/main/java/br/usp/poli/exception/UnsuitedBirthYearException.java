package br.usp.poli.exception;

public class UnsuitedBirthYearException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public UnsuitedBirthYearException(String mensagem) {
		super(mensagem);
	}
}