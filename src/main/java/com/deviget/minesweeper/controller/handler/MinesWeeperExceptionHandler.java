package com.deviget.minesweeper.controller.handler;

import com.deviget.minesweeper.api.response.ErrorResponse;
import com.deviget.minesweeper.controller.exception.RequestValidationException;
import com.deviget.minesweeper.domain.exception.InternalException;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@ControllerAdvice
public class MinesWeeperExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value={RequestValidationException.class })
	protected ResponseEntity<Object> handleRequestValidationException(RequestValidationException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value={NotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value={InternalException.class})
	protected ResponseEntity<Object> handleRuntimeException(InternalException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value={RuntimeException.class})
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getReasonPhrase());
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
	}
}
