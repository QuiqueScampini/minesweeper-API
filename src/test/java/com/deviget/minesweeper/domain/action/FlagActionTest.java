package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static com.deviget.minesweeper.model.CellContent.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class FlagActionTest {

	private static final int LEFT_FLAGS = 0;
	private static final int MINUS_1_LEFT_FLAGS = LEFT_FLAGS - 1;
	private static final int PLUS_1_LEFT_FLAGS = LEFT_FLAGS + 1;

	@InjectMocks
	private FlagAction action;

	@Mock
	private BoardService boardService;

	@Mock
	private ActionRequest actionRequest;

	@Mock
	private Game game;

	@Mock
	private Cell cell;

	@BeforeEach
	void setUp() {
		openMocks(this);
		when(boardService.retrieveCell(actionRequest,game)).thenReturn(cell);
		when(cell.isHidden()).thenReturn(true);
		when(game.getLeftFlags()).thenReturn(LEFT_FLAGS);
	}

	@Test
	void execute_notHiddenCell_returnsEmptyList() {
		when(cell.isHidden()).thenReturn(false);

		List<Cell> affectedCells = action.execute(actionRequest, game);

		assertEquals(0,affectedCells.size());
	}

	@Test
	void execute_hiddenWithNoneContent_returnsCell() {
		when(cell.getContent()).thenReturn(NONE);

		List<Cell> affectedCells = action.execute(actionRequest, game);

		assertSame(cell,affectedCells.get(0));
		verify(cell).setContent(FLAG);
		verify(game).setLeftFlags(MINUS_1_LEFT_FLAGS);
	}

	@Test
	void execute_hiddenWithFlagContent_returnsCell() {
		when(cell.getContent()).thenReturn(FLAG);

		List<Cell> affectedCells = action.execute(actionRequest, game);

		assertSame(cell,affectedCells.get(0));
		verify(cell).setContent(QUESTION);
		verify(game).setLeftFlags(PLUS_1_LEFT_FLAGS);
	}

	@Test
	void execute_hiddenWithQUESTIONContent_returnsCell() {
		when(cell.getContent()).thenReturn(QUESTION);

		List<Cell> affectedCells = action.execute(actionRequest, game);

		assertSame(cell,affectedCells.get(0));
		verify(cell).setContent(NONE);
		verify(game,never()).setLeftFlags(anyInt());
	}
}