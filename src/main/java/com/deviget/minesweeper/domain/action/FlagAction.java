package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import com.deviget.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FlagAction extends GameAction {

	@Override
	public List<Cell> execute(ActionRequest actionRequest, Game game) {
		Cell cell = this.retrieveCell(game, actionRequest);

		if(!cell.isHidden())
			return Collections.emptyList();

		switch (cell.getContent()) {
			case NONE:
				cell.setContent(CellContent.FLAG);
				game.setLeftFlags(game.getLeftFlags() - 1 );
				break;
			case FLAG:
				cell.setContent(CellContent.QUESTION);
				game.setLeftFlags(game.getLeftFlags() + 1 );
				break;
			case QUESTION:
				cell.setContent(CellContent.NONE);
				break;
		}
		return Collections.singletonList(cell);
	}

}
