package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.web.exception.RequestValidationException;
import com.deviget.minesweeper.domain.factory.BoardFactory;
import com.deviget.minesweeper.domain.model.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.deviget.minesweeper.domain.model.CellContent.REVEALED;

@Component
public class BoardService {

	public static final int MINE_VALUE = -1;

	@Autowired
	private BoardFactory boardFactory;

	public List<List<Cell>> createBoard(int rows, int cols, int mines) {
		return boardFactory.create(rows,cols,mines);
	}

	public Cell retrieveCell(ActionRequest actionRequest, List<List<Cell>> board) {
		int row = actionRequest.getRow();
		int col = actionRequest.getCol();
		this.validate(row,col,board);
		return board.get(row).get(col);
	}

	private void validate(int row, int col, List<List<Cell>> board) {
		if(row < 0 || row >= board.size())
			throw new RequestValidationException("Invalid Row "+ row);

		int colsNumber = board.get(row).size();
		if(col < 0 || col >= colsNumber)
			throw new RequestValidationException("Invalid Col "+ col);
	}

	public boolean isMine(Cell cell){
		return cell.getValue().equals(MINE_VALUE);
	}

	public void revealAllMines(List<List<Cell>> board) {
		List<Cell> mines = board.stream()
								.flatMap( row -> row.stream().filter(this::isMine))
								.collect(Collectors.toList());
		this.reveal(mines,board);
	}

	public void revealCell(Cell cell,List<List<Cell>> board) {
		cell.setContent(REVEALED);
		if(cell.getValue().equals(0)){
			List<Cell> adjacent = this.retrieveAdjacentNotRevealed(cell, board);
			this.reveal(adjacent,board);
		}
	}

	public boolean allButMinesRevealed(List<List<Cell>> board) {
		return board.stream()
				.allMatch( row ->
						row.stream().allMatch( cell ->
								REVEALED.equals(cell.getContent()) ||
										this.isMine(cell)
						));
	}

	private void reveal(List<Cell> cells, List<List<Cell>> board) {
		for(Cell cell : cells){
			this.revealCell(cell,board);
		}
	}

	private List<Cell> retrieveAdjacentNotRevealed(Cell cell, List<List<Cell>> board) {
		int cellRow = cell.getRow();
		int cellCol = cell.getCol();

		return board.stream()
				.flatMap( row -> row.stream()
									.filter( aCell ->!REVEALED.equals(aCell.getContent()))
									.filter( aCell -> this.areAdjacent(aCell,cellRow,cellCol)))
				.collect(Collectors.toList());
	}

	private boolean areAdjacent(Cell aCell, int cellRow, int cellCol) {
		int aCellRow = aCell.getRow();
		int aCellCol = aCell.getCol();

		return aCellRow >= (cellRow - 1) &&
				aCellRow <= (cellRow + 1) &&
				aCellCol >= (cellCol - 1) &&
				aCellCol <= (cellCol + 1);
	}
}
