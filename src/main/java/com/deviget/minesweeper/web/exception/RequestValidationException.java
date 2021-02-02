package com.deviget.minesweeper.web.exception;

public class RequestValidationException extends RuntimeException{

	public RequestValidationException(String message) {
		super(message);
	}
}
