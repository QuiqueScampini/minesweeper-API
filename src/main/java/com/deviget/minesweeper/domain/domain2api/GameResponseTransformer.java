package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class GameResponseTransformer {

	@Autowired
	private CellsResponseTransformer cellsResponseTransformer;

	public GameResponse transform(Game game) {
		return new GameResponse(game.getId(),game.getUser(),game.getGameTime(),game.getLeftFlags(), null);
	}

	public GameResponse transform(Game game, Cell affectedCell, int row, int col) {
		CellResponse affected = this.cellsResponseTransformer.transform(affectedCell, row, col);
		List<CellResponse> affectedCells = Collections.singletonList(affected);
		return new GameResponse(game.getId(),game.getUser(),game.getGameTime(),game.getLeftFlags(), affectedCells);
	}
}
