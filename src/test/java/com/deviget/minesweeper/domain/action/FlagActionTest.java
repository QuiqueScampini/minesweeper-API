package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.deviget.minesweeper.domain.model.CellContent.*;
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
		when(boardService.retrieveCell(same(actionRequest),any())).thenReturn(cell);
		when(game.getLeftFlags()).thenReturn(LEFT_FLAGS);
	}

	@Test
	void execute_hiddenWithNoneContent_setFlagContentSubtractFlag() {
		when(cell.getContent()).thenReturn(NONE);

		action.execute(actionRequest, game);

		verify(cell).setContent(FLAG);
		verify(game).setLeftFlags(MINUS_1_LEFT_FLAGS);
	}

	@Test
	void execute_hiddenWithFlagContent_setQuestionContentAddFlag() {
		when(cell.getContent()).thenReturn(FLAG);

		action.execute(actionRequest, game);

		verify(cell).setContent(QUESTION);
		verify(game).setLeftFlags(PLUS_1_LEFT_FLAGS);
	}

	@Test
	void execute_hiddenWithQUESTIONContent_setNoneContent() {
		when(cell.getContent()).thenReturn(QUESTION);

		action.execute(actionRequest, game);

		verify(cell).setContent(NONE);
		verify(game,never()).setLeftFlags(anyInt());
	}

	@Test
	void execute_REVEALEDCell_doNothing() {
		when(cell.getContent()).thenReturn(REVEALED);

		action.execute(actionRequest, game);

		verify(cell,never()).setContent(any());
		verify(game,never()).setLeftFlags(anyInt());
	}
}