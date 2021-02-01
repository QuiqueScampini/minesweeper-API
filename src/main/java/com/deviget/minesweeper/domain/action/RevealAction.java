package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.CellContent;
import com.deviget.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RevealAction extends GameAction {

	@Override
	public List<Cell> execute(ActionRequest actionRequest, Game game) {
		Cell cell = this.retrieveCell(game, actionRequest);

		//If it's not hidden or the content is a flag then we do nothing
		if(!cell.isHidden() || cell.getContent().equals(CellContent.FLAG))
			return Collections.emptyList();

		Integer value = cell.getValue();
		if(value < 0 ){
			throw new RuntimeException("Game Over");
		}

		return this.revealCell(cell,game);
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
		return this.retrieveAdjacent(cell,game).stream()
				.map( adjacentCell -> this.revealCell(adjacentCell,game))
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	private List<Cell> retrieveAdjacent(Cell cell, Game game) {
		List<Cell> adjacent = new ArrayList<>();

		Cell[][] board = game.getBoard();

		int cellRow = cell.getRow();
		int celCol = cell.getCol();
		for(int row = cellRow -1; row < cellRow + 1; row ++){
			for(int col=celCol -1; col <= celCol +1; col++){
				if(row > 0 && col > 0) {
					adjacent.add(board[row][col]);
				}
			}
		}
		return adjacent;
	}
}
