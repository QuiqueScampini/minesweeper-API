package com.deviget.minesweeper.domain.factory;

import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.model.Cell;
import com.deviget.minesweeper.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;

import static com.deviget.minesweeper.model.GameStatus.CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GameFactoryTest {

	private static final String USER = "Quique";
	private static final int MINES = 2;
	private static final List<List<Cell>> BOARD = Collections.emptyList();

	@InjectMocks
	private GameFactory factory;

	@Mock
	private BoardFactory boardFactory;

	@Mock
	private GameRequest gameRequest;

	@BeforeEach
	void setUp() {
		openMocks(this);
		when(gameRequest.getUser()).thenReturn(USER);
		when(gameRequest.getMines()).thenReturn(MINES);

		when(boardFactory.create(gameRequest)).thenReturn(BOARD);
	}

	@Test
	void create() {
		Game game = factory.create(gameRequest);

		assertEquals(USER, game.getUser());
		assertEquals(CREATED, game.getStatus());
		assertSame(BOARD, game.getBoard());
		assertEquals(MINES, game.getLeftFlags());
	}
}