package com.deviget.minesweeper.api.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {

	private HttpStatus status;
	private LocalDateTime timestamp;
	private String message;

	private ErrorResponse() {
		this.timestamp =  LocalDateTime.now();
	}

	public ErrorResponse(HttpStatus status) {
		this();
		this.status = status;
	}

	public ErrorResponse(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = ex.getLocalizedMessage();
	}

	public ErrorResponse(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
}
