package io.breezil.orquestra.exception;

public class OrquestraException extends RuntimeException {
	private static final long serialVersionUID = -2503795936132058266L;

	public OrquestraException(String errorMessage) {
		super(errorMessage);
	}

}
