package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.factory.BoardFactory;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.deviget.minesweeper.model.CellContent.REVEALED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class BoardServiceTest {

	private static final int ROWS = 3;
	private static final int COLS = 3;
	private static final int MINES = 2;

	@InjectMocks
	private BoardService service;

	@Mock
	private BoardFactory boardFactory;

	@Mock
	private ActionRequest actionRequest;

	@Mock
	private Game game;

	private List<List<Cell>> board;

	@BeforeEach
	void setUp() {
		openMocks(this);
		board = this.mockBoard();
		when(game.getBoard()).thenReturn(board);
	}

	@Test
	void createBoard() {
		service.createBoard(ROWS, COLS, MINES);
		verify(boardFactory).create(ROWS,COLS,MINES);
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
		Cell cell = board.get(0).get(0);

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertContainingCell(cells, 0, 1);
		assertContainingCell(cells, 1, 0);
		assertContainingCell(cells, 1, 1);
	}

	@Test
	void retrieveAdjacent_borderCell() {
		Cell cell = board.get(1).get(2);

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertContainingCell(cells, 0, 1);
		assertContainingCell(cells, 0, 2);
		assertContainingCell(cells, 1, 1);
		assertContainingCell(cells, 2, 1);
		assertContainingCell(cells, 2, 2);
	}

	@Test
	void retrieveAdjacent_InnerCell() {
		Cell cell = board.get(1).get(1);

		List<Cell> cells = service.retrieveAdjacent(cell, game);

		assertContainingCell(cells, 0, 0);
		assertContainingCell(cells, 0, 1);
		assertContainingCell(cells, 0, 2);

		assertContainingCell(cells, 1, 0);
		assertContainingCell(cells, 1, 2);

		assertContainingCell(cells, 2, 0);
		assertContainingCell(cells, 2, 1);
		assertContainingCell(cells, 2, 2);
	}

	@Test
	public void allButMinesRevealed_false() {
		Cell mine = board.get(0).get(1);
		when(mine.getValue()).thenReturn(-1);

		assertFalse(service.allButMinesRevealed(game));
	}

	@Test
	public void allButMinesRevealed_true() {
		convertToMine(0,0);
		convertToMine(0,1);
		convertToMine(0,2);
		
		revealCell(1,0);
		revealCell(1,1);
		revealCell(1,2);

		revealCell(2,0);
		revealCell(2,1);
		revealCell(2,2);

		assertTrue(service.allButMinesRevealed(game));
	}

	@Test
	public void retrieveMines() {
		convertToMine(0,0);
		convertToMine(1,2);

		List<Cell> hiddenMines = service.retrieveMines(game);

		assertContainingCell(hiddenMines,0,0);
		assertContainingCell(hiddenMines,1,2);
	}

	@Test
	public void isMine_true(){
		convertToMine(1,2);
		Cell mine = board.get(1).get(2);

		assertTrue(service.isMine(mine));
	}

	@Test
	public void isMine_false(){
		Cell cell = board.get(1).get(2);

		assertFalse(service.isMine(cell));
	}

	private void assertContainingCell(List<Cell> cells, int row, int col) {
		assertTrue(cells.stream().anyMatch(c -> c.getRow() == row && c.getCol() == col));
	}

	private void revealCell(int row, int col) {
		when(board.get(row).get(col).getContent()).thenReturn(REVEALED);
	}

	private void convertToMine(int row, int col) {
		when(board.get(row).get(col).getValue()).thenReturn(-1);
	}

	private List<List<Cell>> mockBoard() {
		List<List<Cell>> cells = new ArrayList<>();
		for(int row = 0; row< ROWS; row++){
			List<Cell> actualRow = new ArrayList<>();
			cells.add(actualRow);
			for(int col = 0; col< COLS; col++){
				Cell cellMock = Mockito.mock(Cell.class);
				when(cellMock.getRow()).thenReturn(row);
				when(cellMock.getCol()).thenReturn(col);
				when(cellMock.getValue()).thenReturn(1);
				actualRow.add(cellMock);
			}
		}
		return cells;
	}
}