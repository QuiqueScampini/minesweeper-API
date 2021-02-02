package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import com.deviget.minesweeper.domain.factory.BoardFactory;
import com.deviget.minesweeper.model.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.deviget.minesweeper.domain.service.BoardService.MINE_VALUE;
import static com.deviget.minesweeper.model.CellContent.NONE;
import static com.deviget.minesweeper.model.CellContent.REVEALED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
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

	private List<List<Cell>> board;

	@BeforeEach
	void setUp() {
		openMocks(this);
		board = this.mockBoard();
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

		Cell cell = service.retrieveCell(actionRequest,board);

		assertEquals(2,cell.getRow());
		assertEquals(1,cell.getCol());
	}

	@Test
	void retrieveCell_InvalidRow() {
		when(actionRequest.getRow()).thenReturn(6);
		when(actionRequest.getCol()).thenReturn(1);

		assertThrows(NotFoundException.class, () -> service.retrieveCell(actionRequest,board));
	}

	@Test
	void retrieveCell_InvalidCol() {
		when(actionRequest.getRow()).thenReturn(1);
		when(actionRequest.getCol()).thenReturn(-10);

		assertThrows(NotFoundException.class, () -> service.retrieveCell(actionRequest,board));
	}


	@Test
	public void isMine_true(){
		convertToMine(1,2);
		Cell mine = this.getCell(1, 2);

		assertTrue(service.isMine(mine));
	}

	@Test
	public void isMine_false(){
		Cell cell = this.getCell(1, 2);

		assertFalse(service.isMine(cell));
	}

	@Test
	public void revealAllMines() {
		convertToMine(0,0);
		convertToMine(1,2);

		service.revealAllMines(board);

		verify(this.getCell(0, 0)).setContent(REVEALED);
		verify(this.getCell(1, 2)).setContent(REVEALED);
	}

	@Test
	public void revealCell_notRevealsAdjacent() {
		Cell cell = this.getCell(0, 0);

		service.revealCell(cell,board);

		verify(cell).setContent(REVEALED);
	}

	@Test
	public void revealCell_RevealsAdjacent() {
		Cell cell = this.getCell(0, 0);
		Cell cell1 = this.getCell(0, 1);
		Cell cell2 = this.getCell(1, 0);
		Cell cell3 = this.getCell(1, 1);
		Cell cell4 = this.getCell(2, 2);

		revealCell(0,1);
		when(cell.getValue()).thenReturn(0);

		/*This is to avoid a loop
		* as this is a mock the setContent
		* will not change it's value so it will
		* end in an infinite loop*/
		revealCell(0,0);

		service.revealCell(cell,board);

		verify(cell).setContent(REVEALED);
		verify(cell1,never()).setContent(REVEALED);
		verify(cell2).setContent(REVEALED);
		verify(cell3).setContent(REVEALED);
		verify(cell4,never()).setContent(REVEALED);
	}

	@Test
	public void allButMinesRevealed_false() {
		Cell mine = getCell(0, 1);
		when(mine.getValue()).thenReturn(-1);

		assertFalse(service.allButMinesRevealed(board));
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

		assertTrue(service.allButMinesRevealed(board));
	}

	private void convertToMine(int row, int col) {
		when(getCell(row, col).getValue()).thenReturn(MINE_VALUE);
	}

	private void revealCell(int row, int col) {
		when(this.getCell(row,col).getContent()).thenReturn(REVEALED);
	}

	private Cell getCell(int row, int col) {
		return board.get(row).get(col);
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
				when(cellMock.getContent()).thenReturn(NONE);
				actualRow.add(cellMock);
			}
		}
		return cells;
	}
}