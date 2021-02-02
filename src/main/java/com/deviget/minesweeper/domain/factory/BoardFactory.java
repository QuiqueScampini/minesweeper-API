package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.deviget.minesweeper.model.CellContent.NONE;

@Component
public class BoardFactory {

	private static final int MINE_VALUE = -1;

	@Autowired
	private MinesPositionGenerator minesPositionsGenerator;

	public List<List<Cell>> create(GameRequest gameRequest) {
		int rows = gameRequest.getRows();
		int cols = gameRequest.getCols();
		int mines = gameRequest.getMines();

		List<Pair<Integer, Integer>> minesPositions = this.minesPositionsGenerator.generate(rows,cols,mines);

		return IntStream.range(0, rows)
				.boxed()
				.map(rowNumber -> this.createRow(rowNumber, cols, minesPositions))
				.collect(Collectors.toList());
	}

	private List<Cell> createRow(Integer rowNumber, Integer cols, List<Pair<Integer, Integer>> minesPositions) {
		return IntStream.range(0, cols)
				.boxed()
				.map( colNumber -> this.createCell(rowNumber,colNumber,minesPositions))
				.collect(Collectors.toList());
	}

	private Cell createCell(Integer row, Integer col, List<Pair<Integer, Integer>>  minesPositions) {
		boolean isMine = this.isMine(row, col, minesPositions);
		Integer value = isMine? MINE_VALUE : this.calculateValue(row, col, minesPositions);
		return new Cell(row, col,value,NONE);
	}

	private boolean isMine(int row, int col, List<Pair<Integer, Integer>> minesPositions) {
		return minesPositions.stream().anyMatch(minePosition -> matchPosition(row,col,minePosition));
	}

	private boolean matchPosition(int row, int col, Pair<Integer,Integer> minePosition) {
		return minePosition.getFirst().equals(row) && minePosition.getSecond().equals(col);
	}

	/**
	 *	Having a list of mines positions and my row, col i can define my value
	 *	From that list I keep the positions that surround me and count them.
	 * **/
	private Integer calculateValue(int row, int col, List<Pair<Integer, Integer>> minesPositions) {
		long count = minesPositions.stream().filter(minePosition ->
						isInAdjacentRow(row,minePosition) &&
						isInAdjacentCol(col,minePosition)
		).count();
		return Math.toIntExact(count);
	}

	private boolean isInAdjacentRow(int row, Pair<Integer,Integer> minePosition) {
		return minePosition.getFirst() >= row - 1 && minePosition.getFirst() <= row + 1;
	}

	private boolean isInAdjacentCol(int col, Pair<Integer,Integer> minePosition) {
		return minePosition.getSecond() >= col - 1 && minePosition.getSecond() <= col + 1;
	}
}
