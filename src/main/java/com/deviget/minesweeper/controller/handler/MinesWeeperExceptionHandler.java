package com.deviget.minesweeper.controller.handler;

import com.deviget.minesweeper.api.response.ErrorResponse;
import com.deviget.minesweeper.controller.exception.RequestValidationException;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MinesWeeperExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value={RequestValidationException.class })
	protected ResponseEntity<Object> handleRequestValidationException(RequestValidationException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value={NotFoundException.class })
	protected ResponseEntity<Object> handleGameNotFoundException(NotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
}
