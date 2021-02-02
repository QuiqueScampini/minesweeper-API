package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.factory.BoardFactory;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.deviget.minesweeper.model.CellContent.REVEALED;

@Component
public class BoardService {

	private static final int MINE_VALUE = -1;

	@Autowired
	private BoardFactory boardFactory;

	public List<List<Cell>> createBoard(int rows, int cols, int mines) {
		return boardFactory.create(rows,cols,mines);
	}

	public Cell retrieveCell(ActionRequest actionRequest, Game game) {
		return game.getBoard()
				.get(actionRequest.getRow())
				.get(actionRequest.getCol());
	}

	public List<Cell> retrieveAdjacent(Cell cell, Game game) {
		List<List<Cell>> board = game.getBoard();

		int maxRow = board.size();
		int maxCol = board.get(0).size();

		int cellRow = cell.getRow();
		int cellCol = cell.getCol();

		List<Cell> adjacent = new ArrayList<>();
		for(int row = cellRow -1; row <= cellRow + 1; row ++){
			for(int col=cellCol -1; col <= cellCol +1; col++) {
				if (!sameCell(row,col,cellRow,cellCol) &&
						validRow(maxRow,row) &&
						validCol(maxCol,col)) {
					adjacent.add(board.get(row).get(col));
				}
			}
		}
		return adjacent;
	}

	public boolean allButMinesRevealed(Game game) {
		return game.getBoard().stream()
				.allMatch( row ->
					row.stream().allMatch( cell ->
							REVEALED.equals(cell.getContent()) ||
								this.isMine(cell)
				));
	}

	public List<Cell> retrieveMines(Game game) {
		return game.getBoard().stream()
				.flatMap( row -> row.stream().filter(this::isMine))
				.collect(Collectors.toList());
	}

	private boolean isMine(Cell cell){
		return cell.getValue().equals(MINE_VALUE);
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
