package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameResponseTransformer {

	@Autowired
	private CellsResponseTransformer cellsResponseTransformer;

	public GameResponse transform(Game game) {
		return new GameResponse.Builder()
				.withId(game.getId())
				.withUser(game.getUser())
				.withGameTime(game.getGameTime())
				.withLeftFlags(game.getLeftFlags())
				.build();
	}

	public GameResponse transform(Game game, List<Cell> affectedCells) {
		return new GameResponse.Builder()
				.withId(game.getId())
				.withUser(game.getUser())
				.withGameTime(game.getGameTime())
				.withLeftFlags(game.getLeftFlags())
				.withAffectedCells(this.cellsResponseTransformer.transform(affectedCells))
				.build();
	}
}
