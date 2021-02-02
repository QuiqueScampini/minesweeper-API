package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameFactory {

	@Autowired
	private BoardService boardFactory;

	public Game create(GameRequest gameRequest) {
		String user = gameRequest.getUser();
		int rows = gameRequest.getRows();
		int cols = gameRequest.getCols();
		int mines = gameRequest.getMines();
		List<List<Cell>> board = boardFactory.createBoard(rows,cols,mines);

		return new Game(user, board, mines);
	}
}
