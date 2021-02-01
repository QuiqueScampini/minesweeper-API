package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.deviget.minesweeper.model.GameStatus.*;

@Component
public class RevealAction implements GameAction {

	@Autowired
	private BoardService boardService;

	@Override
	public List<Cell> execute(ActionRequest actionRequest, Game game) {

		if(CREATED.equals(game.getStatus()))
			game.setStatus(IN_PROGRESS);

		Cell cell = boardService.retrieveCell(actionRequest,game);

		//If it's not hidden or the content is a flag then we do nothing
		if(!cell.isHidden() || cell.getContent().equals(CellContent.FLAG))
			return Collections.emptyList();

		if(cell.getValue() < 0 ) {
			game.setStatus(LOST);
			return this.revealAllMines(game);
		}

		List<Cell> affectedCells = this.revealCell(cell, game);
		if(boardService.allButMinesRevealed(game))
			game.setStatus(WON);

		return affectedCells;
	}

	private List<Cell> revealAllMines(Game game) {
		List<Cell> mines = boardService.retrieveHiddenMines(game);

		for(Cell mine : mines)
			mine.setHidden(false);

		return mines;
	}

	private List<Cell> revealCell(Cell cell, Game game) {
		List<Cell> result = new ArrayList<>();
		cell.setHidden(false);
		result.add(cell);
		if(cell.getValue().equals(0)){
			result.addAll(this.revealAdjacent(cell,game));
		}
		return result;
	}

	private List<Cell> revealAdjacent(Cell cell, Game game) {
		return this.boardService.retrieveAdjacent(cell,game)
				.stream()
				.filter(Cell::isHidden)
				.map( adjacentCell -> this.revealCell(adjacentCell,game))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}
}
