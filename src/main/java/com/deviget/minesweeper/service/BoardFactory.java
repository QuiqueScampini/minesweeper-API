package com.deviget.minesweeper.service;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class BoardFactory {

	private final Random randomGenerator = new Random();

	public Cell[][] create(GameRequest gameRequest) {
		int rows = gameRequest.getRows();
		int cols = gameRequest.getCols();
		int mines = gameRequest.getMines();

		List<Pair<Integer, Integer>> minesPositions = this.minesPositions(rows,cols,mines);

		Cell[][] cells = new Cell[rows][cols];
		for(int row = 0; row< rows; row++){
			for(int col = 0; col< cols; col++){
				cells[row][col] = this.createCell(row, col, minesPositions);
			}
		}
		return cells;
	}

	private Cell createCell(int row, int col, List<Pair<Integer, Integer>>  minesPositions) {
		boolean isMine = this.isMine(row, col, minesPositions);
		long value = this.calculateValue(row, col, minesPositions);
		return new Cell(isMine,value);
	}

	private boolean isMine(int row, int col, List<Pair<Integer, Integer>> minesPositions) {
		return minesPositions.stream().anyMatch(minePosition ->
				minePosition.getFirst().equals(row) && minePosition.getSecond().equals(col)
		);
	}

	/**
	 *	Having a list of mines positions and my row, col i can define my value
	 *	From that list I keep the positions that surround me and count them.
	 * **/
	private long calculateValue(int row, int col, List<Pair<Integer, Integer>> minesPositions) {
		return minesPositions.stream().filter(minePosition ->
				minePosition.getFirst() >= row - 1 && minePosition.getFirst() <= row + 1 &&
						minePosition.getSecond() >= col - 1 && minePosition.getSecond() <= col + 1
		).count();
	}


	/**
	 * This method gets the random position of the mines
	 * 		1. We get the board size
	 * 	  	2. We get 3 random numbers between 0 and 8
	 * 	  	3. We calculate the (row,col) position in base of that numbers to create a list of Pairs
	 * Ej:
	 * 	We have 3 mines in a 3x3 board
	 * 		1. 3*3= 9
	 * 		2. For example (4,5,8)
	 * 		3. 4 -> (1,1) , 5 -> (1,2) and 8 -> (2,2) being 1 -> (0,0).
	 *
	 * **/
	private List<Pair<Integer, Integer>> minesPositions(int rows, int cols, int mines){
		int boardSize = cols * rows;
		return this.randomGenerator.ints(0, boardSize)
				.distinct()
				.limit(mines)
				.mapToObj(positionNumber -> Pair.of(positionNumber / rows, positionNumber % cols))
				.collect(Collectors.toList());
	}
}
