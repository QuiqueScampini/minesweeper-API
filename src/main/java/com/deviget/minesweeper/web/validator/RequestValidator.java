package com.deviget.minesweeper.web.validator;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.web.exception.RequestValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator {

	private final int minCols;
	private final int maxRows;
	private final int minRows;
	private final int maxCols;
	private final int minMines;
	private final int maxMines;

	public RequestValidator( @Value("${game.min.rows}") int minRows,
							 @Value("${game.max.rows}") int maxRows,
							 @Value("${game.min.cols}") int minCols,
							 @Value("${game.max.cols}") int maxCols,
							 @Value("${game.min.mines}") int minMines,
							 @Value("${game.max.mines}") int maxMines){
		this.minRows = minRows;
		this.maxRows = maxRows;
		this.minCols = minCols;
		this.maxCols = maxCols;
		this.minMines = minMines;
		this.maxMines = maxMines;

	}

	public void validateRequest(GameRequest gameRequest) {
		int cols = gameRequest.getCols();
		int rows = gameRequest.getRows();
		int mines = gameRequest.getMines();

		if(rows < this.minRows || rows > maxRows)
			throw new RequestValidationException("Can't create game: Rows must be between " + this.minRows + " and " + this.maxRows);

		if(cols < this.minCols || cols > maxCols)
			throw new RequestValidationException("Can't create game: Cols must be between " + this.minCols + " and " + this.maxCols);

		if(mines < this.minMines || mines > maxMines)
			throw new RequestValidationException("Can't create game: Mines must be between " + this.minMines + " and " + this.maxMines);

		if(mines >= cols * rows)
			throw new RequestValidationException("Can't create game: There are more mines than cells");
	}
}
