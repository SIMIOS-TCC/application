package br.usp.poli.exception;

public class NoIntersectionPointsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NoIntersectionPointsException(String mensagem) {
		super(mensagem);
	}
}