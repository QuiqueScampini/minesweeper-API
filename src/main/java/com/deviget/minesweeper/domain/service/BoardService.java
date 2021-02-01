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
		int maxRow = board.length;
		int maxCol = board[0].length;
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();

		List<Cell> adjacent = new ArrayList<>();
		for(int row = cellRow -1; row <= cellRow + 1; row ++){
			for(int col=cellCol -1; col <= cellCol +1; col++) {
				if (!sameCell(row,col,cellRow,cellCol) &&
						validRow(maxRow,row) &&
						validCol(maxCol,col)) {
					adjacent.add(board[row][col]);
				}
			}
		}
		return adjacent;
	}

	private boolean sameCell(int row, int col, int cellRow, int cellCol) {
		return row == cellRow && col == cellCol;
	}

	private boolean validCol(int maxCol, int col) {
		return col >= 0 && col < maxCol;
	}

	private boolean validRow(int maxRow, int row) {
		return row >= 0 && row < maxRow;
	}

}
