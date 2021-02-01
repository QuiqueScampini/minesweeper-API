package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameFactory {

	@Autowired
	private BoardFactory boardFactory;

	public Game create(GameRequest gameRequest) {
		String user = gameRequest.getUser();
		List<List<Cell>> board = boardFactory.create(gameRequest);
		int mines = gameRequest.getMines();

		return new Game(user, board, mines);
	}
}
