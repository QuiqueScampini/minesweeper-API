package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.CellResponse;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameResponseTransformer {

	@Autowired
	private CellsResponseTransformer cellsResponseTransformer;

	public GameResponse transform(Game game) {
		return new GameResponse(game.getId(),game.getUser(),game.getGameTime(),game.getLeftFlags(), null);
	}

	public GameResponse transform(Game game, List<Cell> affectedCells) {
		List<CellResponse> affectedCellsResponse = this.transform(affectedCells);
		return new GameResponse(game.getId(),game.getUser(),game.getGameTime(),game.getLeftFlags(), affectedCellsResponse);
	}

	private List<CellResponse> transform(List<Cell> affectedCells) {
		return affectedCells.stream()
				.map( affectedCell -> this.cellsResponseTransformer.transform(affectedCell))
				.collect(Collectors.toList());
	}
}
