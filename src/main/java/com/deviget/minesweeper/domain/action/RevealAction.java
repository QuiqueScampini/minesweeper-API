package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.CellContent;
import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.domain.service.GameOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.deviget.minesweeper.domain.model.CellContent.FLAG;
import static com.deviget.minesweeper.domain.model.CellContent.REVEALED;
import static com.deviget.minesweeper.domain.model.GameStatus.LOST;
import static com.deviget.minesweeper.domain.model.GameStatus.WON;

@Component
public class RevealAction implements GameAction {

	private static final List<CellContent> IGNORE_CONTENTS = Arrays.asList(REVEALED, FLAG);

	@Autowired
	private BoardService boardService;

	@Autowired
	private GameOperation gameOperation;

	@Override
	public void execute(ActionRequest actionRequest, Game game) {
		List<List<Cell>> board = game.getBoard();
		Cell cell = boardService.retrieveCell(actionRequest, board);

		//If game is finished we do nothing
		if(gameOperation.isFinished(game) || IGNORE_CONTENTS.contains(cell.getContent()))
			return;

		//If game is paused we put it in progress and start counting time
		gameOperation.startIfPaused(game);

		//If we selected a mine we lost
		if(boardService.isMine(cell) ) {
			game.setStatus(LOST);
			boardService.revealAllMines(board);
		}

		//We reveal our cell concurrently
		boardService.revealCell(cell, board);

		//If all is revealed instead of mines then we won!
		if(boardService.allButMinesRevealed(board))
			game.setStatus(WON);
	}
}
