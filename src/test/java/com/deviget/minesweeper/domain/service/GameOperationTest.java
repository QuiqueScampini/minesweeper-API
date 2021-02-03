package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.domain.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.deviget.minesweeper.domain.model.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class GameOperationTest {

	private static final int GAME_TIME = 10;

	@InjectMocks
	private GameOperation operation;

	@Mock
	private Game game;

	@BeforeEach
	void setUp() {
		openMocks(this);
		when(game.getStatus()).thenReturn(IN_PROGRESS);
	}

	@Test
	void isFinished_False() {
		assertFalse(operation.isFinished(game));
	}

	@Test
	void isFinished_True() {
		when(game.getStatus()).thenReturn(WON);

		assertTrue(operation.isFinished(game));
	}

	@Test
	void startIfPaused_Not_Paused_DoNothing() {
		operation.startIfPaused(game);

		verify(game,never()).setStatus(IN_PROGRESS);
		verify(game,never()).setLastUpdateTime(any());
	}

	@Test
	void startIfPaused_Paused_Start() {
		when(game.getStatus()).thenReturn(CREATED);

		operation.startIfPaused(game);

		verify(game).setStatus(IN_PROGRESS);
		verify(game).setLastUpdateTime(any());
	}

	@Test
	void calculatePausedGameTime(){
		when(game.getGameTime()).thenReturn(GAME_TIME);
		when(game.getStatus()).thenReturn(CREATED);

		int gameTime = operation.calculateGameTime(game);

		assertEquals(GAME_TIME,gameTime);
	}
}