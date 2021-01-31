package com.deviget.minesweeper.controller.exception;

public class RequestValidationException extends RuntimeException{

	public RequestValidationException(String message) {
		super(message);
	}
}
