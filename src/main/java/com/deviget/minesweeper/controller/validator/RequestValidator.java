package com.deviget.minesweeper.controller.validator;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.controller.exception.RequestValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

	private final int minCols;
	private final int minRows;
	private final int minMines;

	public RequestValidator( @Value("${game.min.rows}") int minRows,
							 @Value("${game.min.cols}") int minCols,
							 @Value("${game.min.mines}") int minMines){
		this.minCols = minCols;
		this.minRows = minRows;
		this.minMines = minMines;
	}

	public void validateRequest(GameRequest gameRequest) {
		int cols = gameRequest.getCols();
		int rows = gameRequest.getRows();
		int mines = gameRequest.getMines();

		if(rows < this.minRows)
			throw new RequestValidationException("Can't create game: Minimum rows size is " + this.minRows);

		if(cols < this.minCols)
			throw new RequestValidationException("Can't create game: Minimum cols size is " + this.minCols);

		if(mines < this.minMines)
			throw new RequestValidationException("Can't create game: Minimum mines size is " + this.minMines);

		if(mines >= cols * rows)
			throw new RequestValidationException("Can't create game: There are more mines than cells");
	}

	public void validateRequest(ActionRequest actionRequest) {
		//TODO Validate
	}
}
