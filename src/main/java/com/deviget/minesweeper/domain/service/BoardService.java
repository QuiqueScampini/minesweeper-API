package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoardService {

	public Cell retrieveCell(ActionRequest actionRequest, Game game) {
		int row = actionRequest.getRow();
		int col = actionRequest.getCol();
		return game.getBoard()[row][col];
	}

	public List<Cell> retrieveAdjacent(Cell cell, Game game) {
		Cell[][] board = game.getBoard();
		int cellRow = cell.getRow();
		int celCol = cell.getCol();

		List<Cell> adjacent = new ArrayList<>();
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
