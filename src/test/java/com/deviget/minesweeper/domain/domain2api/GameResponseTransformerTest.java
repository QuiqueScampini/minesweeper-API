package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.api.response.RowResponse;
import com.deviget.minesweeper.domain.model.Game;
import com.deviget.minesweeper.domain.model.GameStatus;
import com.deviget.minesweeper.domain.service.GameOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.deviget.minesweeper.domain.model.GameStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class GameResponseTransformerTest {

	private static final Integer ID = 271088;
	private static final String USER = "Nowe";
	private static final GameStatus GAME_STATUS = IN_PROGRESS;
	private static final int FLAGS = -2;
	private static final int TIME = 400;

	@InjectMocks
	private GameResponseTransformer responseTransformer;

	@Mock
	private CellsResponseTransformer cellsResponseTransformer;

	@Mock
	private GameOperation gameOperation;

	@Mock
	private Game game;

	private final List<RowResponse> board = new ArrayList<>();

	@BeforeEach
	void setUp() {
		openMocks(this);

		when(game.getId()).thenReturn(ID);
		when(game.getUser()).thenReturn(USER);
		when(game.getStatus()).thenReturn(GAME_STATUS);
		when(game.getLeftFlags()).thenReturn(FLAGS);
		when(gameOperation.calculateGameTime(game)).thenReturn(TIME);

		when(cellsResponseTransformer.transform(anyList())).thenReturn(board);
	}

	@Test
	void transformListOfGames_nullUser() {
		GamesResponse gamesResponse = responseTransformer.transform(Collections.singletonList(game), null);

		assertEquals(1,gamesResponse.getGames().size());
		assertGameResponse(gamesResponse.getGames().get(0));
		assertNull(gamesResponse.getUser());
	}

	@Test
	void transformListOfGames() {
		GamesResponse gamesResponse = responseTransformer.transform(Collections.singletonList(game), USER);

		assertEquals(1,gamesResponse.getGames().size());
		assertGameResponse(gamesResponse.getGames().get(0));
		assertEquals(USER, gamesResponse.getUser());
	}

	@Test
	void transform() {
		GameResponse gameResponse = responseTransformer.transform(game);
		assertGameResponse(gameResponse);
	}

	private void assertGameResponse(GameResponse gameResponse) {
		assertEquals(ID, gameResponse.getId());
		assertEquals(USER, gameResponse.getUser());
		assertEquals(GAME_STATUS, gameResponse.getStatus());
		assertSame(board, gameResponse.getBoard());
		assertEquals(TIME, gameResponse.getGameTime());
		assertEquals(FLAGS, gameResponse.getLeftFlags());
	}
}