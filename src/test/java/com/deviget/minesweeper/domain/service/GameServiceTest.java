package com.deviget.minesweeper.domain.service;

import com.deviget.minesweeper.api.request.ActionRequest;
import com.deviget.minesweeper.api.request.GameRequest;
import com.deviget.minesweeper.api.response.GameResponse;
import com.deviget.minesweeper.api.response.GamesResponse;
import com.deviget.minesweeper.domain.action.FlagAction;
import com.deviget.minesweeper.domain.action.RevealAction;
import com.deviget.minesweeper.domain.domain2api.GameResponseTransformer;
import com.deviget.minesweeper.domain.exception.NotFoundException;
import com.deviget.minesweeper.domain.factory.GameFactory;
import com.deviget.minesweeper.model.Game;
import com.deviget.minesweeper.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.deviget.minesweeper.api.request.Action.FLAG;
import static com.deviget.minesweeper.api.request.Action.REVEAL;
import static com.deviget.minesweeper.model.GameStatus.PAUSED;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.util.ReflectionTestUtils.setField;

class GameServiceTest {

	private static int ID = 271088;
	private static final String USER = "HIPPIE";

	@InjectMocks
	private GameService service;

	@Mock
	private GameFactory gameFactory;

	@Mock
	private GameRepository gameRepository;

	@Mock
	private GameResponseTransformer gameResponseTransformer;

	@Mock
	private FlagAction flagAction;

	@Mock
	private RevealAction revealAction;

	@Mock
	private GameRequest gameRequest;

	@Mock
	private Game game;

	@Mock
	private GameResponse gameResponse;

	@Mock
	private GamesResponse gamesResponse;

	@Mock
	private ActionRequest actionRequest;

	@BeforeEach
	void setUp() {
		openMocks(this);
		service = new GameService(flagAction,revealAction);
		setField(service,"gameFactory",gameFactory);
		setField(service,"gameRepository",gameRepository);
		setField(service,"gameResponseTransformer",gameResponseTransformer);

		when(gameResponseTransformer.transform(game)).thenReturn(gameResponse);
		when(gameRepository.findById(ID)).thenReturn(Optional.of(game));
	}

	@Test
	void createGame() {
		when(gameFactory.create(gameRequest)).thenReturn(game);

		GameResponse actualGameResponse = service.createGame(gameRequest);

		assertSame(gameResponse,actualGameResponse);
		verify(gameRepository).save(game);
	}

	@Test
	public void retrieveGamesByUser() {
		when(gameResponseTransformer.transform(anyList(),same(USER))).thenReturn(gamesResponse);
		when(gameRepository.findAllByUser(USER)).thenReturn(Collections.singletonList(game));

		GamesResponse actualGamesResponse = service.retrieveGames(USER);

		assertSame(gamesResponse,actualGamesResponse);
	}

	@Test
	public void retrieveGamesByUser_NotFound() {
		when(gameRepository.findAllByUser(USER)).thenReturn(Collections.emptyList());

		assertThrows(NotFoundException.class, () -> service.retrieveGames(USER));

		verify(gameResponseTransformer,never()).transform(anyList(),same(USER));
	}

	@Test
	public void retrieveAllGames() {
		when(gameResponseTransformer.transform(anyList(),same(null))).thenReturn(gamesResponse);
		when(gameRepository.findAll()).thenReturn(Collections.singletonList(game));

		GamesResponse actualGamesResponse = service.retrieveGames(null);

		assertSame(gamesResponse,actualGamesResponse);
	}

	@Test
	void retrieveGame(){
		GameResponse actualGameResponse = service.retrieveGame(ID);
		assertSame(gameResponse,actualGameResponse);
	}

	@Test
	void pauseGame(){
		GameResponse actualGameResponse = service.pauseGame(ID);
		verify(game).setStatus(PAUSED);
		assertSame(gameResponse,actualGameResponse);
		verify(gameRepository).save(game);
	}

	@Test
	void executeAction_FLAG() {
		when(actionRequest.getAction()).thenReturn(FLAG);

		GameResponse actualGameResponse = service.executeAction(ID, actionRequest);

		assertSame(gameResponse,actualGameResponse);
		verify(flagAction).execute(actionRequest,game);
		verify(revealAction,never()).execute(actionRequest,game);
	}

	@Test
	void executeAction_REVEAL() {
		when(actionRequest.getAction()).thenReturn(REVEAL);

		GameResponse actualGameResponse = service.executeAction(ID, actionRequest);

		assertSame(gameResponse,actualGameResponse);
		verify(flagAction,never()).execute(actionRequest,game);
		verify(revealAction).execute(actionRequest,game);
	}

	@Test
	void executeAction_ID_NotFound() {
		when(gameRepository.findById(ID)).thenReturn(Optional.empty());

		assertThrows(NotFoundException.class, () -> service.executeAction(ID, actionRequest));

		verify(gameRepository,never()).save(game);
		verify(gameResponseTransformer,never()).transform(game);
		verify(flagAction,never()).execute(actionRequest,game);
		verify(revealAction,never()).execute(actionRequest,game);
	}
}