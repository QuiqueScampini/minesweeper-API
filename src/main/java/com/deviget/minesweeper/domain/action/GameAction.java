package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;

import java.util.List;

public abstract class GameAction {

	public abstract List<Cell> execute(ActionRequest request, Game game);

	protected Cell retrieveCell(Game game, ActionRequest actionRequest) {
		int row = actionRequest.getRow();
		int col = actionRequest.getCol();
		//TODO validate it exists
		return game.getBoard()[row][col];
	}
}
