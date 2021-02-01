package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BoardServiceTest {

	@InjectMocks
	private BoardService service;

	@Mock
	private ActionRequest actionRequest;

	@Mock
	private Game game;

	private Cell[][] board;

	@BeforeEach
	void setUp() {
		openMocks(this);
		board = this.createBoard(3, 3);
		when(game.getBoard()).thenReturn(board);
		when(game.getRows()).thenReturn(3);
		when(game.getCols()).thenReturn(3);
	}

	@Test
	void retrieveCell() {
		when(actionRequest.getRow()).thenReturn(2);
		when(actionRequest.getCol()).thenReturn(1);

		Cell cell = service.retrieveCell(actionRequest, game);

		assertEquals(2,cell.getRow());
		assertEquals(1,cell.getCol());
	}

	@Test
	void retrieveAdjacent_extremeCell() {
		Cell cell = game.getBoard()[0][0];

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 1 && c.getCol() == 0));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 1 && c.getCol() == 1));
	}

	@Test
	void retrieveAdjacent_borderCell() {
		Cell cell = game.getBoard()[1][2];

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 2));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 1 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 2 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 2 && c.getCol() == 2));
	}

	@Test
	void retrieveAdjacent_InnerCell() {
		Cell cell = game.getBoard()[1][1];

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 0));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 0 && c.getCol() == 2));

		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 1 && c.getCol() == 0));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 1 && c.getCol() == 2));

		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 2 && c.getCol() == 0));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 2 && c.getCol() == 1));
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == 2 && c.getCol() == 2));
	}

	private Cell[][] createBoard(int rows, int cols) {
		Cell[][] cells = new Cell[rows][cols];
		for(int row = 0; row< rows; row++){
			for(int col = 0; col< cols; col++){
				Cell cellMock = Mockito.mock(Cell.class);
				when(cellMock.getRow()).thenReturn(row);
				when(cellMock.getCol()).thenReturn(col);
				cells[row][col] = cellMock;
			}
		}
		return cells;
	}
}