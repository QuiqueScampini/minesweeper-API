package com.deviget.minesweeper.api.response;

import java.time.LocalDateTime;

public class ErrorResponse {

	private ErrorStatus status;
	private LocalDateTime timestamp;
	private String message;

	private ErrorResponse() {
		this.timestamp =  LocalDateTime.now();
	}

	public ErrorResponse(ErrorStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = ex.getLocalizedMessage();
	}

	public ErrorResponse(ErrorStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}

	public ErrorStatus getStatus() {
		return status;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}
}
