package com.deviget.minesweeper.domain.action;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.domain.model.Cell;
import com.deviget.minesweeper.domain.model.CellContent;
import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.domain.service.BoardService;
import com.deviget.minesweeper.domain.service.GameOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.deviget.minesweeper.domain.model.GameStatus.LOST;
import static com.deviget.minesweeper.domain.model.GameStatus.WON;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class RevealActionTest {

	private static final List<List<Cell>> BOARD = Collections.emptyList();

	@InjectMocks
	private RevealAction action;

	@Mock
	private BoardService boardService;

	@Mock
	private GameOperation gameOperation;

	@Mock
	private ActionRequest actionRequest;

	@Mock
	private Game game;

	@Mock
	private Cell cell;

	@BeforeEach
	void setUp() {
		openMocks(this);
		when(game.getBoard()).thenReturn(BOARD);
		when(boardService.retrieveCell(actionRequest,BOARD)).thenReturn(cell);

		when(gameOperation.isFinished(game)).thenReturn(false);
		when(cell.getContent()).thenReturn(CellContent.NONE);
		when(boardService.isMine(cell)).thenReturn(false);
		when(boardService.allButMinesRevealed(BOARD)).thenReturn(false);
	}

	@Test
	void execute_OperationFinished() {
		when(gameOperation.isFinished(game)).thenReturn(true);

		action.execute(actionRequest,game);

		verify(boardService).retrieveCell(actionRequest,BOARD);
		verify(gameOperation,never()).startIfPaused(game);
		verify(boardService,never()).isMine(cell);
		verify(boardService,never()).revealCell(cell,BOARD);
		verify(boardService,never()).allButMinesRevealed(BOARD);
	}

	@Test
	void execute_CellIgnored() {
		when(cell.getContent()).thenReturn(CellContent.FLAG);

		action.execute(actionRequest,game);

		verify(boardService).retrieveCell(actionRequest,BOARD);
		verify(gameOperation,never()).startIfPaused(game);
		verify(boardService,never()).isMine(cell);
		verify(boardService,never()).revealCell(cell,BOARD);
		verify(boardService,never()).allButMinesRevealed(BOARD);
	}

	@Test
	void execute_mine_LostGame() {
		when(boardService.isMine(cell)).thenReturn(true);

		action.execute(actionRequest,game);

		verify(boardService).retrieveCell(actionRequest,BOARD);
		verify(gameOperation).startIfPaused(game);
		verify(game).setStatus(LOST);
		verify(boardService).revealAllMines(BOARD);
		verify(boardService).revealCell(cell,BOARD);
		verify(boardService).allButMinesRevealed(BOARD);
	}

	@Test
	void execute_CommonCell() {
		action.execute(actionRequest,game);

		verify(boardService).retrieveCell(actionRequest,BOARD);
		verify(gameOperation).startIfPaused(game);
		verify(game,never()).setStatus(LOST);
		verify(boardService,never()).revealAllMines(BOARD);
		verify(boardService).revealCell(cell,BOARD);
		verify(boardService).allButMinesRevealed(BOARD);
		verify(game,never()).setStatus(WON);
	}

	@Test
	void execute_CommonCell_WON() {
		when(boardService.allButMinesRevealed(BOARD)).thenReturn(true);

		action.execute(actionRequest,game);

		verify(boardService).retrieveCell(actionRequest,BOARD);
		verify(gameOperation).startIfPaused(game);
		verify(game,never()).setStatus(LOST);
		verify(boardService,never()).revealAllMines(BOARD);
		verify(boardService).revealCell(cell,BOARD);
		verify(boardService).allButMinesRevealed(BOARD);
		verify(game).setStatus(WON);
	}


}