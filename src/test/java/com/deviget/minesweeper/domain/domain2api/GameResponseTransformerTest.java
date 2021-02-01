package com.deviget.minesweeper.domain.domain2api;

import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.RowResponse;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.model.GameStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.deviget.minesweeper.model.GameStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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
	private GameTimeResolver gameTimeResolver;

	@Mock
	private Game game;

	@BeforeEach
	void setUp() {
		openMocks(this);

		when(game.getId()).thenReturn(ID);
		when(game.getUser()).thenReturn(USER);
		when(game.getStatus()).thenReturn(GAME_STATUS);
		when(game.getLeftFlags()).thenReturn(FLAGS);
		when(gameTimeResolver.resolve(game)).thenReturn(TIME);
	}

	@Test
	void transform() {
		List<RowResponse> board = new ArrayList<>();
		when(cellsResponseTransformer.transform(anyList())).thenReturn(board);

		GameResponse gameResponse = responseTransformer.transform(game);
		assertEquals(ID,gameResponse.getId());
		assertEquals(USER,gameResponse.getUser());
		assertEquals(GAME_STATUS,gameResponse.getStatus());
		assertSame(board,gameResponse.getBoard());
		assertEquals(TIME,gameResponse.getGameTime());
		assertEquals(FLAGS,gameResponse.getLeftFlags());
	}
}