package com.trieutruong.webpage.exception;

public class BadInputException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6920391475424879456L;

	public BadInputException() {
		super();
	}

	public BadInputException(String message) {
		super(message);
	}

	public BadInputException(Throwable cause) {
        super(cause);
    }

	public BadInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
