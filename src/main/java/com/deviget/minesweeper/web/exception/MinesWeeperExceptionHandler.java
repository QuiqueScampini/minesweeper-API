package com.deviget.minesweeper.web.exception;

import com.deviget.minesweeper.api.response.ErrorResponse;
import com.deviget.minesweeper.domain.exception.InternalException;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.deviget.minesweeper.api.response.ErrorStatus.*;

@ControllerAdvice
public class MinesWeeperExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(MinesWeeperExceptionHandler.class);

	@ExceptionHandler(value={RequestValidationException.class })
	protected ResponseEntity<Object> handleRequestValidationException(RequestValidationException ex, WebRequest request) {
		LOGGER.error("RequestValidationException {}",ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}

	@ExceptionHandler(value={NotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
		LOGGER.error("NotFoundException {}",ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(value={InternalException.class})
	protected ResponseEntity<Object> handleRuntimeException(InternalException ex, WebRequest request) {
		LOGGER.error("NotFoundException {}",ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, ex);
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(value={RuntimeException.class})
	protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
		LOGGER.error("RuntimeException {}",ex.getMessage());
		ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR, "Internal Server Error");
		return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
