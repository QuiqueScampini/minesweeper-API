package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import com.deviget.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RevealAction implements GameAction {

	@Override
	public List<Cell> execute(ActionRequest actionRequest, Game game) {
		Cell cell = this.retrieveCell(game, actionRequest);

		//If it's not hidden or the content is a flag then we do nothing
		if(!cell.isHidden() || cell.getContent().equals(CellContent.FLAG))
			return Collections.emptyList();

		return this.revealCell(cell,game);
	}

	private List<Cell> revealCell(Cell cell, Game game) {
		return Collections.emptyList();
	}

	private Cell retrieveCell(Game game, ActionRequest actionRequest) {
		int row = actionRequest.getRow();
		int col = actionRequest.getCol();
		//TODO validate it exists
		return game.getBoard()[row][col];
	}
}
