package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.CellContent;
import com.deviget.minesweeper.domain.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.deviget.minesweeper.domain.model.CellContent.FLAG;
import static com.deviget.minesweeper.domain.model.CellContent.REVEALED;
import static com.deviget.minesweeper.domain.model.GameStatus.*;

@Component
public class RevealAction implements GameAction {

	private static final List<CellContent> IGNORE_CONTENTS = Arrays.asList(REVEALED, FLAG);

	@Autowired
	private BoardService boardService;

	@Override
	public void execute(ActionRequest actionRequest, Game game) {
		List<List<Cell>> board = game.getBoard();

		if(CREATED.equals(game.getStatus()))
			game.setStatus(IN_PROGRESS);

		Cell cell = boardService.retrieveCell(actionRequest, board);

		//If it's not hidden or the content is a flag then we do nothing
		if(IGNORE_CONTENTS.contains(cell.getContent()))
			return;

		if(boardService.isMine(cell) ) {
			game.setStatus(LOST);
			boardService.revealAllMines(board);
		}

		boardService.revealCell(cell, board);

		if(boardService.allButMinesRevealed(board))
			game.setStatus(WON);
	}
}
