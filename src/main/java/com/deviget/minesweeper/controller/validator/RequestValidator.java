package com.deviget.minesweeper.controller.validator;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.controller.exception.RequestValidationException;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

	public void validateRequest(GameRequest gameRequest) {
		int cols = gameRequest.getCols();
		int rows = gameRequest.getRows();
		int mines = gameRequest.getMines();

		if(mines >= cols * rows)
			throw new RequestValidationException("Can't create game with more or same mines than cells");
	}
}
