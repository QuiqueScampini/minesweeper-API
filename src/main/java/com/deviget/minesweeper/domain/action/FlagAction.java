package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.deviget.minesweeper.model.CellContent.*;

@Component
public class FlagAction implements GameAction {

	@Autowired
	private BoardService boardService;

	@Override
	public void execute(ActionRequest actionRequest, Game game) {
		Cell cell = boardService.retrieveCell(actionRequest, game.getBoard());

		switch (cell.getContent()) {
			case NONE:
				cell.setContent(FLAG);
				game.setLeftFlags(game.getLeftFlags() - 1 );
				break;
			case FLAG:
				cell.setContent(QUESTION);
				game.setLeftFlags(game.getLeftFlags() + 1 );
				break;
			case QUESTION:
				cell.setContent(NONE);
				break;
			default:
				break;
		}
	}
}
